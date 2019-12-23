package model.beans;

public interface UpdateReference {
	
	public void notifyUpdate(String newId);
	public void updateReference(String className, String oldId, String newId);

}
