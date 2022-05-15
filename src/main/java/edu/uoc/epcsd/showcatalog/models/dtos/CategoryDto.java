package edu.uoc.epcsd.showcatalog.models.dtos;

import edu.uoc.epcsd.showcatalog.models.db.entities.Category;
import lombok.*;

/***
 *  Data transfer object for {@link Category}.
 **/
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String name;
    private String description;
}
