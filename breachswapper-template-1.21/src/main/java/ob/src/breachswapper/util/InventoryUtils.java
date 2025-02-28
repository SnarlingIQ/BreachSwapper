package ob.src.breachswapper.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    
    public static void selectSlot(PlayerEntity player, int slot) {
        player.getInventory().selectedSlot = slot;
    }

    public static int findItemInInventory(PlayerEntity player, Class<? extends Item> itemClass) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (itemClass.isInstance(stack.getItem())) {
                return i;
            }
        }
        return -1;
    }
} 