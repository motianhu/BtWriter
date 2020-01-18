package com.smona.btwriter.ble;

import java.util.UUID;

public class CharacterBean {
    private UUID serviceUUID;
    private UUID characterUUID;

    public CharacterBean(UUID serviceUUID, UUID characterUUID) {
        this.serviceUUID = serviceUUID;
        this.characterUUID = characterUUID;
    }

    public UUID getCharacterUUID() {
        return characterUUID;
    }

    public UUID getServiceUUID() {
        return serviceUUID;
    }
}
