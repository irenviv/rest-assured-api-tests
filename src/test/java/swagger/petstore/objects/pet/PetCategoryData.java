package swagger.petstore.objects.pet;

public class PetCategoryData {
    private Integer id;
    private String name;

    public PetCategoryData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
