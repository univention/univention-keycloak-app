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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Policies which apply for this object.
 */
@ApiModel(description = "Policies which apply for this object.")
@JsonPropertyOrder({
  UsersUserPolicies.JSON_PROPERTY_POLICIES_UMC,
  UsersUserPolicies.JSON_PROPERTY_POLICIES_PWHISTORY,
  UsersUserPolicies.JSON_PROPERTY_POLICIES_DESKTOP
})
@JsonTypeName("users_user_policies")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-10-25T17:17:28.719252+02:00[Europe/Berlin]")
public class UsersUserPolicies {
  public static final String JSON_PROPERTY_POLICIES_UMC = "policies/umc";
  private List<String> policiesUmc = null;

  public static final String JSON_PROPERTY_POLICIES_PWHISTORY = "policies/pwhistory";
  private List<String> policiesPwhistory = null;

  public static final String JSON_PROPERTY_POLICIES_DESKTOP = "policies/desktop";
  private List<String> policiesDesktop = null;


  public UsersUserPolicies policiesUmc(List<String> policiesUmc) {

    this.policiesUmc = policiesUmc;
    return this;
  }

  public UsersUserPolicies addPoliciesUmcItem(String policiesUmcItem) {
    if (this.policiesUmc == null) {
      this.policiesUmc = new ArrayList<>();
    }
    this.policiesUmc.add(policiesUmcItem);
    return this;
  }

   /**
   * Policy: UMC
   * @return policiesUmc
  **/
  @jakarta.annotation.Nullable
  @ApiModelProperty(value = "Policy: UMC")
  @JsonProperty(JSON_PROPERTY_POLICIES_UMC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getPoliciesUmc() {
    return policiesUmc;
  }


  @JsonProperty(JSON_PROPERTY_POLICIES_UMC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPoliciesUmc(List<String> policiesUmc) {
    this.policiesUmc = policiesUmc;
  }


  public UsersUserPolicies policiesPwhistory(List<String> policiesPwhistory) {

    this.policiesPwhistory = policiesPwhistory;
    return this;
  }

  public UsersUserPolicies addPoliciesPwhistoryItem(String policiesPwhistoryItem) {
    if (this.policiesPwhistory == null) {
      this.policiesPwhistory = new ArrayList<>();
    }
    this.policiesPwhistory.add(policiesPwhistoryItem);
    return this;
  }

   /**
   * Policy: Passwords
   * @return policiesPwhistory
  **/
  @jakarta.annotation.Nullable
  @ApiModelProperty(value = "Policy: Passwords")
  @JsonProperty(JSON_PROPERTY_POLICIES_PWHISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getPoliciesPwhistory() {
    return policiesPwhistory;
  }


  @JsonProperty(JSON_PROPERTY_POLICIES_PWHISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPoliciesPwhistory(List<String> policiesPwhistory) {
    this.policiesPwhistory = policiesPwhistory;
  }


  public UsersUserPolicies policiesDesktop(List<String> policiesDesktop) {

    this.policiesDesktop = policiesDesktop;
    return this;
  }

  public UsersUserPolicies addPoliciesDesktopItem(String policiesDesktopItem) {
    if (this.policiesDesktop == null) {
      this.policiesDesktop = new ArrayList<>();
    }
    this.policiesDesktop.add(policiesDesktopItem);
    return this;
  }

   /**
   * Policy: Desktop
   * @return policiesDesktop
  **/
  @jakarta.annotation.Nullable
  @ApiModelProperty(value = "Policy: Desktop")
  @JsonProperty(JSON_PROPERTY_POLICIES_DESKTOP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getPoliciesDesktop() {
    return policiesDesktop;
  }


  @JsonProperty(JSON_PROPERTY_POLICIES_DESKTOP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPoliciesDesktop(List<String> policiesDesktop) {
    this.policiesDesktop = policiesDesktop;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsersUserPolicies usersUserPolicies = (UsersUserPolicies) o;
    return Objects.equals(this.policiesUmc, usersUserPolicies.policiesUmc) &&
        Objects.equals(this.policiesPwhistory, usersUserPolicies.policiesPwhistory) &&
        Objects.equals(this.policiesDesktop, usersUserPolicies.policiesDesktop);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policiesUmc, policiesPwhistory, policiesDesktop);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsersUserPolicies {\n");
    sb.append("    policiesUmc: ").append(toIndentedString(policiesUmc)).append("\n");
    sb.append("    policiesPwhistory: ").append(toIndentedString(policiesPwhistory)).append("\n");
    sb.append("    policiesDesktop: ").append(toIndentedString(policiesDesktop)).append("\n");
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

