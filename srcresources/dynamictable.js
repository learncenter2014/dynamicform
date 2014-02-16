// template table schema
var template1={
   _id: ObjectId("52da62d0f158e8485a3e29e2"),
   name:'template1',
   displayname:'模版1',
   path:'xml/template1.xml',
   createtime:new Date(),
   modifytime: new Date()
};
db.template.insert(template1);

//template table schema
var template2={
   _id: ObjectId("52da62d0f158e8485a3e29e1"),
   name:'template2',
   path:'xml/template2.xml',
   createtime:new Date(),
   modifytime: new Date()
};
db.template.insert(template2);

db.template.ensureIndex({name:1});

//page table schema, every page will be fetched data from {XXX}_data table.
var page={
  _id:ObjectId("52da62d0f158e8485a3e29e3"),
  name:'page1',
  templatelist:[
       {templatename:'template1',templatedatatable:'template', forignkey: ObjectId("52da62d0f158e8485a3e29e2")},
       {templatename:'template2',templatedatatable:'template', forignkey: ObjectId("52da62d0f158e8485a3e29e1")}
  ],
  createtime:new Date(),
  modifytime: new Date()
};
db.page.insert(page);
db.page.ensureIndex({name:1});

//page1_data table schema, who does maintain mapping info,which real data are respectively stored in template1_data, or template2_data...,etc.
var page1_data={
  _id:ObjectId("52da62d0f158e8485a3e29e4"),
  datamapping:[
    {templatename:'template1', templatedatatable:'template1_data',forignkey:ObjectId("52da62d0f158e8485a3e29e5")},
    {templatename:'template2', templatedatatable:'template2_data',forignkey:ObjectId("52da62d0f158e8485a3e29e6")}
  ],
  createtime:new Date(),
  modifytime: new Date()
};
db.page1_data.insert(page);


//template1_data table schema
var template1_data={
        _id: ObjectId("52da62d0f158e8485a3e29e5"),
        name:'peter',
        age: '29',
        address:'Nanjing,Jiangsu',
        createtime:new Date(),
        modifytime: new Date()
};
db.template1_data.insert(template1_data);

//template1_data table schema
var template2_data={
        _id: ObjectId("52da62d0f158e8485a3e29e6"),
        name:'wit',
        age: '28',
        address:'Nanjing,Jiangsu',
        createtime:new Date(),
        modifytime: new Date()
};
db.template2_data.insert(template2_data);