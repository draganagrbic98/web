package model.support;

public interface ReferenceManager {
	
	public void notifyUpdate(String newId);
	public void updateReference(String className, String oldId, String newId);
	public void notifyRemoval();
	public void removeReference(String className, String id);

}
