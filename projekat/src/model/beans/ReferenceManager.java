package model.beans;

public interface ReferenceManager {
	
	public void notifyUpdate(String newId);
	public void updateReference(String className, String oldId, String newId);
	public void removeReference(String className, String id);
	public void notifyRemoval();

}
