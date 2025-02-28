package ob.src.breachswapper.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.MaceItem;
import ob.src.breachswapper.BreachSwapper;
import ob.src.breachswapper.util.InventoryUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private boolean isSwapping = false;
    private int lastSwordSlot = -1;
    private int swapBackDelay = 0;

    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!BreachSwapper.isBreachSwapEnabled() || isSwapping) {
            return;
        }

        ItemStack mainHandItem = player.getMainHandStack();

        if (mainHandItem.getItem() instanceof SwordItem) {
            lastSwordSlot = player.getInventory().selectedSlot;

            int maceSlot = InventoryUtils.findItemInInventory(player, MaceItem.class);
            if (maceSlot != -1) {
                isSwapping = true;
                // Switch to mace for the hit
                InventoryUtils.selectSlot(player, maceSlot);
                // Set delay for switching back
                swapBackDelay = 2; // 2 ticks delay
                isSwapping = false;
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (swapBackDelay > 0) {
            swapBackDelay--;
            if (swapBackDelay == 0 && lastSwordSlot != -1) {
                PlayerEntity player = (PlayerEntity) (Object) this;
                isSwapping = true;
                InventoryUtils.selectSlot(player, lastSwordSlot);
                isSwapping = false;
                lastSwordSlot = -1;
            }
        }
    }
} 