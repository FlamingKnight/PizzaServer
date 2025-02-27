package io.github.willqi.pizzaserver.server.entity;

public abstract class LivingEntity extends Entity {

    private float health;
    private float maxHealth;

    private float absorption;
    private float maxAbsorption;

    private float movementSpeed;


    public float getHealth() {
        return this.health;
    }

    public void setHealth(float health) {
        this.health = Math.max(0, Math.min(health, this.getMaxHealth()));

        if (this.getHealth() <= 0) {
            // TODO: kill
        }
    }

    public float getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = Math.max(0, maxHealth);
        this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));
    }

    public float getAbsorption() {
        return this.absorption;
    }

    public void setAbsorption(float absorption) {
        this.absorption = Math.max(0, Math.min(absorption, this.getMaxAbsorption()));
    }

    public float getMaxAbsorption() {
        return this.maxAbsorption;
    }

    public void setMaxAbsorption(float maxAbsorption) {
        this.maxAbsorption = Math.max(0, maxAbsorption);
        this.setAbsorption(Math.min(this.getAbsorption(), this.getMaxAbsorption()));
    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = Math.max(0, movementSpeed);
    }

}
