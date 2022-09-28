package co.com.sofkoin.beta.application.gateways.rest;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestCryptoResponse extends HashMap<String, Map<String, Double>> {}
