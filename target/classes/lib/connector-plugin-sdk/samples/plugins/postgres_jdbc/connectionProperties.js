(function propertiesbuilder(attr) {
    var props = {};
    var DBUSER= "1"
    var DBPASSWORD = "1"
    var USERNAME= "omp_user"
    var USERPASSWORD = "shinhan@1"
 if((attr[connectionHelper.attributePassword]==USERPASSWORD))
    {
        props["user"] = DBUSER
        props["password"] = DBPASSWORD
    }else
    {
        props["user"] = "";
        props["password"] = "";
    }
    if (attr[connectionHelper.attributeSSLMode] == "require") {
        props["ssl"] = "true";
        props["sslmode"] = "require";
    }

    return props;
})

