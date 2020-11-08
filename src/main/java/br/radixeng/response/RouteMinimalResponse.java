package br.radixeng.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/** Minimum route calculation response class
 * @author Janaina Militão
 */
@Getter @Setter
public class RouteMinimalResponse {

    private int distance;

    private List<String> path;
}
