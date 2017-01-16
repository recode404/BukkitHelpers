import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Formats ItemStacks with custom names and lores.
 * Formats Skulls with custom names and owners.
 */
public class ItemFormatter {
	
	private ItemStack item;
	
	public ItemFormatter(ItemStack item) {
		this.item = item;
	}
	
	//Set an ItemStack's display name
	public ItemStack setName(String name) {
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	//Set an ItemStack's lore with multiple lines separated by commas. Ex. "Line 1,Line 2,Line 3"
	public ItemStack setLore(String lore) {
		ItemMeta meta = item.getItemMeta();
		
		String[] lines = lore.split(",");
		meta.setLore(Arrays.asList(lines));
		item.setItemMeta(meta);
		return item;
	}
	
	//Set ItemStack's owner if ItemStack is a Skull
	public ItemStack setSkullOwner(String owner) {
		if(item.getType() != Material.SKULL) //checks that item is a skull before changing owner
			return item;
		
		SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
		skullMeta.setOwner(owner);
		skullMeta.setDisplayName(owner+"'s Head");
		item.setItemMeta(skullMeta);
		return item;
	}
	
	public ItemStack getItem() {
		return item;
	}

}
