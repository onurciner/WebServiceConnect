# WebServiceConnect 
Connecting to webservices from Android devices is now easy. The sending and receiving of data to and from webservices can be customized in a desired way and can be done by a single method.

##Installation <img src="https://jitpack.io/v/10uroi/WebServiceConnect.svg">

To use the library, first include it your project using Gradle

        allprojects {
            repositories {
                jcenter()
                maven { url "https://jitpack.io" }
            }
        }
and:

        dependencies {
                compile 'com.github.10uroi:WebServiceConnect:1.0.1'
        }
        
finally: Adding permissions to AndroidManifest.xml
        
        <uses-permission android:name="android.permission.INTERNET"/>
        
##Using - While receiving data
### Return String
        try {
            String obj = (String) new WebServiceGetData()
                    .setUrl("http://domainname.com/rest/sync/43") //URL LINK
                    .connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
                
### Return JSON
        try {
            ArrayList<entityName> objs = (ArrayList<entityName>) new WebServiceGetData()
                    .setUrl("http://domainname.com/rest/sync/43")
                    .setReturnType(ReturnType.JSON)
                    .connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
#### Other parameters
    .setConnectTimeout(.)
    .setReadTimeout(.)
    .setRequestMethod(MethodType.POST) //MethodType.PUT, MethodType.DELETE, MethodType.GET, MethodType.PATCH
    
##Using - Sending data
### Sending a Json object 
        try {
            new WebServiceSendData()
                    .setUrl("http://domainname.com/rest/upload/43")
                    .setData(JsonValue)
                    .setRequestMethod(MethodType.POST)
                    .setRequestProperty(RequestPropertyType.APPLICATION_JSON)
                    .connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
                
### Sending a Xml object
        try {
            new WebServiceSendData()
                    .setUrl("http://domainname.com/rest/upload/44")
                    .setData(XmlValue)
                    .setRequestMethod(MethodType.POST)
                    .setCharacter("UTF-8")
                    .setRequestProperty(RequestPropertyType.APPLICATION_XML)
                    .setRequestProperty("Token","785asf4423a3")
                    .connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
### Php webservice POST sending
        try {
            new WebServiceSendData()
                    .setUrl("http://domainname.com/rest/upload.php")
                    .setData(VALUES)// => String VALUES = "name=nameValue & surname=surnameValue & age=ageValue";
                    .setRequestMethod(MethodType.POST)
                    .setRequestProperty(RequestPropertyType.APPLICATION_X_WWW_FORM_URLENCODED)
                    .connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

#### Data transmission types
    APPLICATION_JSON,
    MULTIPART_FORM_DATA,
    APPLICATION_X_WWW_FORM_URLENCODED,
    APPLICATION_XML,
    APPLICATION_BASE64,
    APPLICATION_OCTET_STREAM,
    TEXT_PLAIN,
    TEXT_CSS,
    TEXT_HTML,
    APPLICATION_JAVASCRIPT
    
#### Other parameters
    .setCharacter(.)
    .setConnectTimeout(.)
    .setReadTimeout(.)
    .setRequestProperty("Token","785asf4423a3")
    .setRequestMethod(MethodType.POST) //MethodType.PUT, MethodType.DELETE, MethodType.GET, MethodType.PATCH
    
#### Note
Data sender can get return value.
        
        String result = new WebServiceSendData()
                        .setUrl("http://domainname.com/rest/upload.php")
                        .setData(VALUES)// => String VALUES = "name=nameValue & surname=surnameValue & age=ageValue";
                        .setRequestMethod(MethodType.POST)
                        .setRequestProperty(RequestPropertyType.APPLICATION_X_WWW_FORM_URLENCODED)
                        .connect();
