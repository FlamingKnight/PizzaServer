# PizzaServer
PizzaServer is a side project of mine that has the goal of being Minecraft Bedrock server software designed for all versions of 1.16 higher than 1.16.100.

## Special Thanks
- [Cloudburst](https://github.com/CloudburstMC) for providing the RakNet implementation as well as helping guide me through implementing the Bedrock protocol.
  - Minecraft files sent over the network (e.g. `biome_definitions.nbt` or `runtime_item_states.json`) are retrieved through Cloudburst's [Data](https://github.com/CloudburstMC/Data) repository.
- The contributors over at [wiki.vg/Bedrock_Protocol](https://wiki.vg/Bedrock_Protocol) for providing packet documentation.