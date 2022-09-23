package co.com.sofkoin.beta.domain.common.values.identities;

import co.com.sofka.domain.generic.Identity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class UserID extends Identity {
  public UserID() {
    super();
  }

  public UserID(String value) {
    super(value);
  }
}
