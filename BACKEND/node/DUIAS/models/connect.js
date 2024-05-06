const mysql = require('mysql');


var con  = mysql.createConnection({
    host: 'localhost',
    user: "root",
    password: '',
    database: "duias",
    
})

con.connect(function(err){
    if(err) throw err;
    console.log("Database Connected Successfully");
})

module.exports = con;