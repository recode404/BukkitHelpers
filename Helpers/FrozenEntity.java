import net.minecraft.server.v1_11_R1.EntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Many people have been struggling to spawn in entities with No AI (Frozen) with proper positions and rotations.
 * This is the simplest solution I have found to fix the rotation problem.
 */
public class FrozenEntity {
	
	private EntityLiving living;
	
	/*Create a frozen entity with an NMS entity. Use this code template to create entities:
	FrozenEntity frozen = new FrozenEntity(new EntityZombie(((CraftWorld)Bukkit.getWorld("world")).getHandle()));
	*/
	
	public FrozenEntity(EntityLiving living) {
		this.living = living;
	}
	
	//spawn in the frozen entity at the given location
	public Entity spawn(Location loc) {
		/* whenever someone at Mojang signs up Codecadamy or a weekend workshop to learn 
		   programming basics we wont need these two lines
		*/
		living.h(loc.getYaw());
		living.i(loc.getYaw());
		
		living.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		living.getWorld().addEntity(living, CreatureSpawnEvent.SpawnReason.CUSTOM);
		((LivingEntity)living.getBukkitEntity()).setAI(false);
		return living.getBukkitEntity();
	}
	
	public Entity getEntity() {
		return living.getBukkitEntity();
	}
	
}
