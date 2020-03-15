//从xml中获取节点内容

function getXmlNode(xml, path) {
    //创建文档对象
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xml, "text/xml");
    console.log(xmlDoc);
    //提取数据
    var countrys = xmlDoc.getElementsByTagName(path);
    var arr = [];
    for (var i = 0; i < countrys.length; i++) {
        arr.push(countrys[i].textContent);
    }
    return xmlDoc;
}