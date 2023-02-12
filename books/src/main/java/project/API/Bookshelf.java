package project.API;

public class Bookshelf {
	private int id;
    private String title;
    private String description;
    private String updated;
    private String created;
    private int volumeCount;
    private String volumesLastUpdated;

    public Bookshelf() {
    }

    public Bookshelf(int id, String title, String description, String updated, String created, int volumeCount, String volumeLastUpdated) {
        this.setId(id);
        this.setTitle(title);
        this.setUpdated(updated);
        this.setCreated(created);
        this.setVolumeCount(volumeCount);
        this.setVolumesLastUpdated(volumeLastUpdated);
    }

    public Bookshelf(Bookshelf bookshelf) {
        this.setId(bookshelf.getId());
        this.setTitle(bookshelf.getTitle());
        this.setDescription(bookshelf.getDescription());
        this.setUpdated(bookshelf.getUpdated());
        this.setCreated(bookshelf.getCreated());
        this.setVolumeCount(bookshelf.getVolumeCount());
        this.setVolumesLastUpdated(bookshelf.getVolumesLastUpdated());
    }

    /*
     * 
     * SETTERS - GETTERS
     * 
     * */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
    	if(this.title == null) return "not availiable";
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
    	if(this.description == null) return "not availiable";
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdated() {
		if(this.updated == null) return "not availiable";
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getCreated() {
		if(this.created == null) return "not availiable";
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}

	public int getVolumeCount() {
        return this.volumeCount;
    }
    public void setVolumeCount(int volumeCount) {
        this.volumeCount = volumeCount;
    }
    
    public String getVolumesLastUpdated() {
    	if(this.volumesLastUpdated == null) return "not availiable";
		return volumesLastUpdated;
	}
	public void setVolumesLastUpdated(String volumesLastUpdated) {
		this.volumesLastUpdated = volumesLastUpdated;
	}
	
	/*
     * 
     * END SETTERS - GETTERS
     * 
     * */
	
	@Override
	public String toString() {
        return (
        		"Id: " + this.getId() + 
        		"\nTitle: " + this.getTitle() + 
        		"\nDescription: " + this.getDescription() +
        		"\nUpdated: " + this.getUpdated() +        		
        		"\nCreated: " + this.getCreated() +        		
        		"\nVolume Count: " + this.getVolumeCount() +
        		"\nVolumes Last Updated: " + this.getVolumesLastUpdated()
        		);
    }
}
