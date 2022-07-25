package com.hamusuke.fallingattack.network.s2c;

import com.hamusuke.fallingattack.network.ClientOnlyPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncFallingAttackS2CPacket {
    private final int playerEntityId;
    private final boolean fallingAttack;
    private final float fallingAttackYPos;
    private final int progress;
    private final float fallingAttackYaw;

    public SyncFallingAttackS2CPacket(FriendlyByteBuf buffer) {
        this.playerEntityId = buffer.readVarInt();
        this.fallingAttack = buffer.readBoolean();
        this.fallingAttackYPos = buffer.readFloat();
        this.progress = buffer.readInt();
        this.fallingAttackYaw = buffer.readFloat();
    }

    public SyncFallingAttackS2CPacket(int playerEntityId, boolean fallingAttack, float fallingAttackYPos, int progress, float yaw) {
        this.playerEntityId = playerEntityId;
        this.fallingAttack = fallingAttack;
        this.fallingAttackYPos = fallingAttackYPos;
        this.progress = progress;
        this.fallingAttackYaw = yaw;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.playerEntityId);
        buffer.writeBoolean(this.fallingAttack);
        buffer.writeFloat(this.fallingAttackYPos);
        buffer.writeInt(this.progress);
        buffer.writeFloat(this.fallingAttackYaw);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientOnlyPacketHandler.handle(this)));
        ctx.get().setPacketHandled(true);
    }

    public int getPlayerEntityId() {
        return this.playerEntityId;
    }

    public boolean isUsingFallingAttack() {
        return this.fallingAttack;
    }

    public float getFallingAttackYPos() {
        return this.fallingAttackYPos;
    }

    public int getProgress() {
        return this.progress;
    }

    public float getFallingAttackYaw() {
        return this.fallingAttackYaw;
    }
}
