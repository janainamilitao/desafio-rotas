package br.radixeng.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/** Response path calculation class of routes by stop number
 * @author Janaina Militão
 */
@Getter @Setter
@AllArgsConstructor
public class RoutePathResponse {

    private String route;

    private int  stops;

    public RoutePathResponse(){ }

}
