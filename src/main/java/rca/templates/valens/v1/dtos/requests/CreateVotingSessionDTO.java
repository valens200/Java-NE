package rca.templates.valens.v1.dtos.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVotingSessionDTO {
    @NotNull
    private String title;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
