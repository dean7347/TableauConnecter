(function dsbuilder(attr) {
 var urlBuilder = "jdbc:vertica://" + attr[connectionHelper.attributeServer] + ":" + attr[connectionHelper.attributePort] + "/" + "1" + "?";
return [urlBuilder];
})
