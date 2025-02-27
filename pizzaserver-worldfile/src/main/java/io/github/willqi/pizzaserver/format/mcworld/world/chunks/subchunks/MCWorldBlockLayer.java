package io.github.willqi.pizzaserver.format.mcworld.world.chunks.subchunks;

import io.github.willqi.pizzaserver.format.api.chunks.subchunks.BlockLayer;
import io.github.willqi.pizzaserver.format.BlockRuntimeMapper;
import io.github.willqi.pizzaserver.format.api.chunks.subchunks.BlockPalette;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import net.daporkchop.lib.common.function.io.IOFunction;

import java.util.*;

public class MCWorldBlockLayer implements BlockLayer {

    private final MCWorldBlockPalette palette;
    private final BlockPalette.Entry[] blocks = new BlockPalette.Entry[4096];


    public MCWorldBlockLayer(MCWorldBlockPalette palette) {
        this.palette = palette;
    }

    @Override
    public BlockPalette getPalette() {
        return this.palette;
    }

    @Override
    public BlockPalette.Entry getBlockEntryAt(int x, int y, int z) {
        return this.blocks[(x << 8) | (z << 4) | y];
    }

    @Override
    public void setBlockEntryAt(int x, int y, int z, BlockPalette.Entry entry) {
        this.blocks[(x << 8) | (z << 4) | y] = entry;
        this.palette.add(entry);
    }

    public void parse(ByteBuf buffer, int bitsPerBlock, int blocksPerWord, int wordsPerChunk) {
        int pos = 0;
        for (int chunk = 0; chunk < wordsPerChunk; chunk++) {
            int word = buffer.readIntLE();  // This integer can store multiple blocks.
            for (int block = 0; block < blocksPerWord; block++) {
                if (pos >= 4096) break;

                int paletteIndex = (word >> (pos % blocksPerWord) * bitsPerBlock) & ((1 << bitsPerBlock) - 1);
                this.blocks[pos] = palette.getEntry(paletteIndex);
                pos++;
            }
        }
    }

    @Override
    public byte[] serializeForDisk() {
        return this.serialize(MCWorldBlockPalette::serializeForDisk);
    }

    @Override
    public byte[] serializeForNetwork(BlockRuntimeMapper runtimeMapper) {
        return this.serialize(palette -> this.palette.serializeForNetwork(runtimeMapper));
    }

    private byte[] serialize(IOFunction<MCWorldBlockPalette, byte[]> paletteSerializer) {
        this.cleanUpPalette();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        int bitsPerBlock = (int)Math.ceil(Math.log(this.palette.getAllEntries().size()) / Math.log(2));
        int blocksPerWord = 32 / bitsPerBlock;
        int wordsPerChunk = (int)Math.ceil(4096d / blocksPerWord);

        buffer.writeByte((bitsPerBlock << 1) | 1);

        int pos = 0;
        for (int chunk = 0; chunk < wordsPerChunk; chunk++) {
            int word = 0;
            for (int block = 0; block < blocksPerWord; block++) {
                if (pos >= 4096) break;
                word |= (this.palette.getPaletteIndex(this.blocks[pos])) << (bitsPerBlock * block);
                pos++;
            }
            buffer.writeIntLE(word);
        }

        buffer.writeBytes(paletteSerializer.apply(this.palette));

        byte[] serialized = new byte[buffer.readableBytes()];
        buffer.readBytes(serialized);
        buffer.release();
        return serialized;
    }

    /**
     * Remove palette entries that are no longer used in this chunk
     */
    private void cleanUpPalette() {
        Set<BlockPalette.Entry> usedEntries = new HashSet<>(Arrays.asList(this.blocks));

        Iterator<BlockPalette.Entry> entryIterator = new HashSet<>(this.palette.getAllEntries()).iterator();
        while (entryIterator.hasNext()) {
            BlockPalette.Entry entry = entryIterator.next();
            if (!usedEntries.contains(entry)) {
                entryIterator.remove();
                this.palette.removeEntry(entry, false);
            }
        }
        this.palette.resize();
    }

}
