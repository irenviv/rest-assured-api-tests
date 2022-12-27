package swagger.petstore.objects.pet;

public class PetTag {

    private Integer id;
    private String name;

    public PetTag(Integer id, String name) {
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
