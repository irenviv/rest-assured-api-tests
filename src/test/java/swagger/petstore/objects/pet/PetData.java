package swagger.petstore.objects.pet;

import java.util.List;

public class PetData {

    private Long id;
    private PetCategoryData petCategory;
    private String name;
    private List<String> photoUrls;
    private List<PetTag> petTags;
    private String status;

    public PetData(Long id, PetCategoryData petCategory, String name, List<String> photoUrls, List<PetTag> petTags, String status) {
        this.id = id;
        this.petCategory = petCategory;
        this.name = name;
        this.photoUrls = photoUrls;
        this.petTags = petTags;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetCategoryData getPetCategory() {
        return petCategory;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public List<PetTag> getPetTags() {
        return petTags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }
        if(o instanceof PetData){
            return true;
        }
        PetData pet = (PetData) o;
        return pet.id == id &&
                pet.petCategory ==petCategory &&
                pet.name == name &&
                pet.photoUrls ==photoUrls &&
                pet.petTags == petTags &&
                pet.status == status;
    }


}
