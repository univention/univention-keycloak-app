/*
 * Univention Directory Manager REST interface
 * Schema definition for the objects in the Univention Directory Manager REST interface
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Object type specific options.
 */
@ApiModel(description = "Object type specific options.")
@JsonPropertyOrder({
  UsersUserOptions.JSON_PROPERTY_PKI
})
@JsonTypeName("users_user_options")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-10-25T17:17:28.719252+02:00[Europe/Berlin]")
public class UsersUserOptions {
  public static final String JSON_PROPERTY_PKI = "pki";
  private Boolean pki = false;


  public UsersUserOptions pki(Boolean pki) {
    
    this.pki = pki;
    return this;
  }

   /**
   * Public key infrastructure account
   * @return pki
  **/
  @jakarta.annotation.Nullable
  @ApiModelProperty(example = "false", value = "Public key infrastructure account")
  @JsonProperty(JSON_PROPERTY_PKI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getPki() {
    return pki;
  }


  @JsonProperty(JSON_PROPERTY_PKI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPki(Boolean pki) {
    this.pki = pki;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsersUserOptions usersUserOptions = (UsersUserOptions) o;
    return Objects.equals(this.pki, usersUserOptions.pki);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pki);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsersUserOptions {\n");
    sb.append("    pki: ").append(toIndentedString(pki)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

