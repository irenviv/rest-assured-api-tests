package swagger.petstore.objects.store;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetOrderData {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public PetOrderData(Long id, Long petId, Integer quantity, String shipDate, String status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    //ship date in request -   "shipDate": "2022-11-30T11:19:14.552Z"
    //ship date in response -  "shipDate": "2022-11-30T11:19:14.552+0000",
    //create pattern for date format - 2022-11-30T11:19:14.552
    public String getShipDate() {
        Pattern regexp = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}");
        Matcher m = regexp.matcher(shipDate);
        if (m.find()){
            return m.group();
        } else {
            System.out.println("Can't find any matchers in ship date");
            return "";
        }
    }

    public String getStatus() {
        return status;
    }

    public Boolean getComplete() {
        return complete;
    }


}
