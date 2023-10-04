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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.UsersUser;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * UsersUserListEmbedded
 */
@JsonPropertyOrder({
  UsersUserListEmbedded.JSON_PROPERTY_UDM_COLON_OBJECT
})
@JsonTypeName("users_user_list__embedded")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-10-25T17:17:28.719252+02:00[Europe/Berlin]")
public class UsersUserListEmbedded {
  public static final String JSON_PROPERTY_UDM_COLON_OBJECT = "udm:object";
  private List<UsersUser> udmColonObject = null;


  public UsersUserListEmbedded udmColonObject(List<UsersUser> udmColonObject) {

    this.udmColonObject = udmColonObject;
    return this;
  }

  public UsersUserListEmbedded addUdmColonObjectItem(UsersUser udmColonObjectItem) {
    if (this.udmColonObject == null) {
      this.udmColonObject = new ArrayList<>();
    }
    this.udmColonObject.add(udmColonObjectItem);
    return this;
  }

   /**
   * Get udmColonObject
   * @return udmColonObject
  **/
  @jakarta.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_UDM_COLON_OBJECT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<UsersUser> getUdmColonObject() {
    return udmColonObject;
  }


  @JsonProperty(JSON_PROPERTY_UDM_COLON_OBJECT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUdmColonObject(List<UsersUser> udmColonObject) {
    this.udmColonObject = udmColonObject;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsersUserListEmbedded usersUserListEmbedded = (UsersUserListEmbedded) o;
    return Objects.equals(this.udmColonObject, usersUserListEmbedded.udmColonObject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(udmColonObject);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsersUserListEmbedded {\n");
    sb.append("    udmColonObject: ").append(toIndentedString(udmColonObject)).append("\n");
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

