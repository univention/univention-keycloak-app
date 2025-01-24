# Univention-directory-manager

This is a Java client to communicate with the UDM rest API in UCS/Nubus.

The code in this folder has been automatically generated using the openapi code generator.

The code was generated using the following command:

`java -jar openapi-generator-cli.jar generate -i ./src/main/resources/openapi.json -o out -g java --skip-validate-spec`

The `openapi-generator-cli.jar` can be downloaded [here](https://openapi-generator.tech/docs/installation/#jar)


# Patches

The generated code is not enough to make the `univention-authenticator` plugin to work.
It needs the following [patch](udm.patch).


```
diff --git extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/api/UsersUserApi.java extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/api/UsersUserApi.java
index 9a551ff..385eea6 100644
--- extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/api/UsersUserApi.java
+++ extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/api/UsersUserApi.java
@@ -239,20 +239,18 @@ public class UsersUserApi {
         Map<String, String> localVarCookieParams = new HashMap<String, String>();
         Map<String, Object> localVarFormParams = new HashMap<String, Object>();

+        // Manually changed
         final String[] localVarAccepts = {
-            "application/hal+json",
             "application/json",
-            "text/html"
         };
         final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
         if (localVarAccept != null) {
             localVarHeaderParams.put("Accept", localVarAccept);
         }

+        // Manually changed
         final String[] localVarContentTypes = {
-            "application/hal+json",
             "application/json",
-            "text/html"
         };
         final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
         if (localVarContentType != null) {
diff --git extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/model/UsersUserProperties.java extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/model/UsersUserProperties.java
index 7f85ec8..3353850 100644
--- extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/model/UsersUserProperties.java
+++ extensions/univention-directory-manager/src/main/java/main/org/openapitools/client/model/UsersUserProperties.java
@@ -1523,6 +1523,11 @@ public class UsersUserProperties {
     return password;
   }

+  // Manually added, it wasn't auto generated
+  public void setPassword(String password) {
+    this.password = password;
+  }
+

   public UsersUserProperties passwordexpiry(LocalDate passwordexpiry) {
     this.passwordexpiry = passwordexpiry;

```



