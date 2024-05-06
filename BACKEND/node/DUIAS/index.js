const express = require('express');
const expressLayouts = require('express-ejs-Layouts')
const ejs = require('ejs');
const path = require('path');
const fileupload = require('express-fileupload');
const bodyparser = require("body-parser");
const session = require("express-session");

const con = require("./models/connect");
var app = express();

app.set('views', path.join(__dirname, "/view/"));
app.set('view engine', 'ejs');
app.set('layout', 'layouts/mainlayouts')
app.use(expressLayouts)
app.use(express.json())
app.use(express.urlencoded());
app.use(bodyparser.json());
app.use(fileupload());
app.use(express.static(__dirname + "/public"));
app.use(session({ secret: 'hello' }));


app.use(function (req, res, next) {
    res.locals.session = req.session;
    next();
});

/*app.get("/", function (req, res) {

    res.render('default/index');
});

app.get("/about", function (req, res) {
    res.render('default/about');
});

app.get("/contact", function (req, res) {
    res.render('default/contact');
});

app.get("/login", function (req, res) {
    res.render('default/login');
});

app.post("/login_user", function (req, res) {
    const { txtemail, txtpwd, selltype } = req.body;
    sess = req.session;
    if (selltype == "1") {
        var query1 = "select * from principal_detail where email_id=? and pwd=? and status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                //alert("Principal Login Successfully");
                //res.redirect("/login");
                res.write("<script>alert('Principal Login Succesfully'); window.location.href='/principal_manage_dep'; </script>")
            }
        })
    } else if (selltype == "2") {
        var query1 = "select * from clerk_detail where email_id=? and pwd=? and clerk_status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                sess.clerkid = result[0].clerk_id;
                //res.write("<script>alert('Clerk Login Succesfully'); window.location.href='/clerk_manage_program'; </script>")
                res.redirect("/clerk_manage_program");
            }
        })
    } else if (selltype == "3") {
        var query1 = "select * from hod_detail where email_id=? and pwd=? and hod_status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                sess.hodid = result[0].hod_id;
                //res.write("<script>alert('Clerk Login Succesfully'); window.location.href='/clerk_manage_program'; </script>")
                res.redirect("/hod_manage_subject");
            }
        })
    } else if (selltype == "4") {
        var query1 = "select * from faculty_detail where email_id=? and pwd=? and faculty_status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                sess.fid = result[0].faculty_id;
                //res.write("<script>alert('Clerk Login Succesfully'); window.location.href='/clerk_manage_program'; </script>")
                res.redirect("/faculty_manage_attendance");
            }
        })
    }
})

app.get("/logout", function (req, res) {
    if (req.session) {
        sess = req.session;
        req.session.destroy(() => {
            res.redirect("/"); //Inside a callback… bulletproof!
        });
    } else {
        res.redirect("/"); //Inside a callback… bulletproof!
    }
})


//principal manage department coding start....
app.get("/principal_manage_dep", function (req, res) {
    let query1 = "select * from dep_master";
    let items = [];
    con.query(query1, (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        res.render("principal/principal_manage_dep", {
            items: items,
            layout: 'layouts/principallayouts',

        });
    });

})

app.post("/add_dept", function (req, res) {
    const { txtname } = req.body;
    let query1 = "select max(dep_id) as dep_id from dep_master";

    con.query(query1, (err, result) => {
        var depid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                depid = row.dep_id + 1;
            } else {
                depid = 1;
            }

            let query2 = "insert into dep_master values(?,?,?)";
            con.query(query2, [depid, txtname, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Department Inserted  Succesfully'); window.location.href='/principal_manage_dep'; </script>")
            })
        }

    })
});

app.get("/principal_edit_dep/:depid", function (req, res) {
    let query1 = "select * from dep_master where dep_id=?";
    let items = [];
    con.query(query1, [req.params.depid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        res.render("principal/principal_edit_dep", {
            items: items,
            layout: 'layouts/principallayouts',

        });
    });
});

app.post("/update_dept", function (req, res) {
    const { txtname, txtdpid } = req.body;
    let query1 = "update dep_master set dep_name=? where dep_id=?";
    con.query(query1, [txtname, txtdpid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Department Updated Succesfully'); window.location.href='/principal_manage_dep'; </script>")
    });
})

app.get("/inactive_dept/:dpid", function (req, res) {

    let query1 = "update dep_master set dep_status=? where dep_id=?";
    con.query(query1, [1, req.params.dpid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Department Inactive Succesfully'); window.location.href='/principal_manage_dep'; </script>")
    });
});

app.get("/active_dept/:dpid", function (req, res) {

    let query1 = "update dep_master set dep_status=? where dep_id=?";
    con.query(query1, [0, req.params.dpid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Department Active Succesfully'); window.location.href='/principal_manage_dep'; </script>")
    });
});



//principal manage department coding end....


//principal manage hod coding start...
app.get("/principal_manage_hod", function (req, res) {
    let query1 = "select * from hod_detail";
    let items = [];
    con.query(query1, function (err, result1) {
        items = JSON.parse(JSON.stringify(result1));
        con.query("select * from dep_master", function (err1, result2) {
            dep_items = JSON.parse(JSON.stringify(result2));
            res.render("principal/principal_manage_hod", {
                items: items,
                ditems: dep_items,
                layout: 'layouts/principallayouts'

            });
        })

    })
});

app.post("/add_hod", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, seldept, selque, txtans } = req.body;
    let query1 = "select max(hod_id) as hod_id from hod_detail";

    con.query(query1, (err, result) => {
        var hodid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                hodid = row.hod_id + 1;
            } else {
                hodid = 1;
            }

            let query2 = "insert into hod_detail values(?,?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [hodid, txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selque, txtans, seldept, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Hod Inserted  Succesfully'); window.location.href='/principal_manage_hod'; </script>")
            })
        }

    })
});

app.get("/inactive_hod/:hodid", function (req, res) {

    let query1 = "update hod_detail set hod_status=? where hod_id=?";
    con.query(query1, [1, req.params.hodid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Hod Inactive Succesfully'); window.location.href='/principal_manage_hod'; </script>")
    });
});

app.get("/active_hod/:hodid", function (req, res) {

    let query1 = "update hod_detail set hod_status=? where hod_id=?";
    con.query(query1, [0, req.params.hodid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Hod Active Succesfully'); window.location.href='/principal_manage_hod'; </script>")
    });
});

app.get("/principal_edit_hod/:hodid", function (req, res) {
    let query1 = "select * from hod_detail where hod_id=?";
    let items = [];
    con.query(query1, [req.params.hodid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        con.query("select * from dep_master", function (err1, result2) {
            dep_items = JSON.parse(JSON.stringify(result2));
            res.render("principal/principal_edit_hod", {
                items: items,
                ditems: dep_items,
                layout: 'layouts/principallayouts',

            });
        });
    });
});


app.post("/update_hod", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, seldept, selque, txtans, txthodid } = req.body;
    let query1 = "update hod_detail set hod_name=?,address=?,city=?,mobile_no=?,email_id=?,pwd=?,gender=?,security_que=?,security_ans=?,dep_id=? where hod_id=?";
    con.query(query1, [txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selque, txtans, seldept, txthodid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Hod Updated Succesfully'); window.location.href='/principal_manage_hod'; </script>")
    });
})

//principal manage hod coding end...




//principal manage Clerk coding start...
app.get("/principal_manage_clerk", function (req, res) {
    let query1 = "select * from clerk_detail";
    let items = [];
    con.query(query1, function (err, result1) {
        items = JSON.parse(JSON.stringify(result1));

        res.render("principal/principal_manage_clerk", {
            items: items,
            layout: 'layouts/principallayouts'

        });


    })
});

app.post("/add_clerk", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, selque, txtans } = req.body;
    let query1 = "select max(clerk_id) as clerk_id from clerk_detail";

    con.query(query1, (err, result) => {
        var clerkid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                clerkid = row.clerk_id + 1;
            } else {
                clerkid = 1;
            }

            let query2 = "insert into clerk_detail values(?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [clerkid, txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selque, txtans, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Clerk Inserted  Succesfully'); window.location.href='/principal_manage_clerk'; </script>")
            })
        }

    })
});

app.get("/inactive_clerk/:clerkid", function (req, res) {

    let query1 = "update clerk_detail set clerk_status=? where clerk_id=?";
    con.query(query1, [1, req.params.clerkid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Clerk Inactive Succesfully'); window.location.href='/principal_manage_clerk'; </script>")
    });
});

app.get("/active_clerk/:clerkid", function (req, res) {

    let query1 = "update clerk_detail set clerk_status=? where clerk_id=?";
    con.query(query1, [0, req.params.clerkid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Clerk Active Succesfully'); window.location.href='/principal_manage_clerk'; </script>")
    });
});

app.get("/principal_edit_clerk/:clerkid", function (req, res) {
    let query1 = "select * from clerk_detail where clerk_id=?";
    let items = [];
    con.query(query1, [req.params.clerkid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);

        res.render("principal/principal_edit_clerk", {
            items: items,

            layout: 'layouts/principallayouts',

        });

    });
});


app.post("/update_clerk", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, selque, txtans, txtclerkid } = req.body;
    let query1 = "update clerk_detail set clerk_name=?,address=?,city=?,mobile_no=?,email_id=?,pwd=?,gender=?,security_que=?,security_ans=? where clerk_id=?";
    con.query(query1, [txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selque, txtans, txtclerkid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Clerk Updated Succesfully'); window.location.href='/principal_manage_clerk'; </script>")
    });
})

//principal manage Clerk coding end..


//clerk manage program coding start...
app.get("/clerk_manage_program", function (req, res) {
    let query1 = "select * from program_master where clerk_id=?";
    let items = [];
    sess = req.session;
    con.query(query1, [sess.clerkid], function (err, result1) {
        items = JSON.parse(JSON.stringify(result1));
        con.query("select * from dep_master", function (err1, result2) {
            dep_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_manage_program", {
                items: items,
                ditems: dep_items,
                layout: 'layouts/clerklayouts'
            });
        })

    })
});

app.post("/add_program", function (req, res) {
    const { txtname, seldept } = req.body;
    let query1 = "select max(program_id) as program_id from program_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var pid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                pid = row.program_id + 1;
            } else {
                pid = 1;
            }

            let query2 = "insert into program_master values(?,?,?,?,?)";
            con.query(query2, [pid, txtname, seldept, sess.clerkid, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Program Inserted Succesfully'); window.location.href='/clerk_manage_program'; </script>")
            })
        }

    })
});

app.get("/inactive_program/:pid", function (req, res) {

    let query1 = "update program_master set program_status=? where program_id=?";
    con.query(query1, [1, req.params.pid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Program Inactive Succesfully'); window.location.href='/clerk_manage_program'; </script>")
    });
});

app.get("/active_program/:pid", function (req, res) {

    let query1 = "update program_master set program_status=? where program_id=?";
    con.query(query1, [0, req.params.pid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Program Active Succesfully'); window.location.href='/clerk_manage_program'; </script>")
    });
});

app.get("/clerk_edit_program/:pid", function (req, res) {
    let query1 = "select * from program_master where program_id=?";
    let items = [];
    con.query(query1, [req.params.pid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        con.query("select * from dep_master", function (err1, result2) {
            dep_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_edit_program", {
                items: items,
                ditems: dep_items,
                layout: 'layouts/clerklayouts',

            });
        });
    });
});


app.post("/update_program", function (req, res) {
    const { txtname, seldept, txtpid } = req.body;
    let query1 = "update program_master set program_name=?,dep_id=? where program_id=?";
    con.query(query1, [txtname, seldept, txtpid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Program Updated Succesfully'); window.location.href='/clerk_manage_program'; </script>")
    });
})

//clerk manage program coding end...



//clerk manage faculty coding start...
app.get("/clerk_manage_faculty", function (req, res) {
    let query1 = "select * from faculty_detail where clerk_id=?";
    let items = [];
    sess = req.session;
    con.query(query1, [sess.clerkid], function (err, result1) {
        items = JSON.parse(JSON.stringify(result1));
        con.query("select * from program_master", function (err1, result2) {
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_manage_faculty", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/clerklayouts'
            });
        })

    })
});

app.post("/add_faculty", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, selprogram, selque, txtans } = req.body;
    let query1 = "select max(faculty_id) as faculty_id from faculty_detail";
    sess = req.session;
    con.query(query1, (err, result) => {
        var fid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                fid = row.faculty_id + 1;
            } else {
                fid = 1;
            }

            let query2 = "insert into faculty_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [fid, txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selque, txtans, selprogram, sess.clerkid, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Faculty Inserted Succesfully'); window.location.href='/clerk_manage_faculty'; </script>")
            })
        }

    })
});

app.get("/inactive_faculty/:fid", function (req, res) {

    let query1 = "update faculty_detail set faculty_status=? where faculty_id=?";
    con.query(query1, [1, req.params.fid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Faculty Inactive Succesfully'); window.location.href='/clerk_manage_faculty'; </script>")
    });
});

app.get("/active_faculty/:fid", function (req, res) {

    let query1 = "update faculty_detail set faculty_status=? where faculty_id=?";
    con.query(query1, [0, req.params.fid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Faculty Active Succesfully'); window.location.href='/clerk_manage_faculty'; </script>")
    });
});

app.get("/clerk_edit_faculty/:fid", function (req, res) {
    let query1 = "select * from faculty_detail where faculty_id=?";
    let items = [];
    con.query(query1, [req.params.fid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        con.query("select * from program_master", function (err1, result2) {
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_edit_faculty", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/clerklayouts',
            });
        });
    });
});


app.post("/update_faculty", function (req, res) {
    const { txtname, txtadd, txtcity, txtmno, gender, txtemail, txtpwd, selprogram, selque, txtans, txtfid } = req.body;
    let query1 = "update faculty_detail set faculty_name=?,address=?,city=?,mobile_no=?,email_id=?,pwd=?,gender=?,program_id=?,security_que=?,security_ans=? where faculty_id=?";
    con.query(query1, [txtname, txtadd, txtcity, txtmno, txtemail, txtpwd, gender, selprogram, selque, txtans, txtfid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Faculty Updated Succesfully'); window.location.href='/clerk_manage_faculty'; </script>")
    });
})

//clerk manage faculty coding end...


//clerk manage division coding start...
app.get("/clerk_manage_division", function (req, res) {
    let query1 = "select * from division_detail where clerk_id=?";
    let items = [];
    sess = req.session;
    con.query(query1, [sess.clerkid], function (err, result1) {
        items = JSON.parse(JSON.stringify(result1));
        con.query("select * from program_master", function (err1, result2) {
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_manage_division", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/clerklayouts'
            });
        })

    })
});

app.post("/add_div", function (req, res) {
    const { txtayear, selprogram, txtcname, txtdiv, selsem } = req.body;
    let query1 = "select max(div_id) as div_id from division_detail";
    sess = req.session;
    con.query(query1, (err, result) => {
        var did = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                did = row.div_id + 1;
            } else {
                did = 1;
            }

            let query2 = "insert into division_detail values(?,?,?,?,?,?,?,?)";
            con.query(query2, [did, txtayear, selprogram, txtcname, selsem, txtdiv, sess.clerkid, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Division Inserted Succesfully'); window.location.href='/clerk_manage_division'; </script>")
            })
        }

    })
});

app.get("/inactive_div/:did", function (req, res) {

    let query1 = "update division_detail set div_status=? where div_id=?";
    con.query(query1, [1, req.params.did], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Division Inactive Succesfully'); window.location.href='/clerk_manage_division'; </script>")
    });
});

app.get("/active_div/:did", function (req, res) {

    let query1 = "update division_detail set div_status=? where div_id=?";
    con.query(query1, [0, req.params.did], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Division Active Succesfully'); window.location.href='/clerk_manage_division'; </script>")
    });
});

app.get("/clerk_edit_division/:did", function (req, res) {
    let query1 = "select * from division_detail where div_id=?";
    let items = [];
    con.query(query1, [req.params.did], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        con.query("select * from program_master", function (err1, result2) {
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("clerk/clerk_edit_division", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/clerklayouts',
            });
        });
    });
});


app.post("/update_div", function (req, res) {
    const { txtayear, selprogram, txtcname, txtdiv, selsem, txtdid } = req.body;
    let query1 = "update division_detail set academic_year=?,program_id=?,class_name=?,semester=?,division=? where div_id=?";
    con.query(query1, [txtayear, selprogram, txtcname, selsem, txtdiv, txtdid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Division Updated Succesfully'); window.location.href='/clerk_manage_division'; </script>")
    });
})

//clerk manage division coding end...


//clerk manage student coding start...
app.get("/clerk_manage_student", function (req, res) {

    var newdate = new Date(new Date().getTime() - (6205 * 24 * 60 * 60 * 1000));
    //console.log(newdate);
    var month = newdate.getMonth() + 1
    if (newdate.getMonth() < 10) {
        month = '0' + month;
    }
    var day = newdate.getDate();
    if (newdate.getDate() < 10) {
        day = '0' + newdate.getDate();
    }
    var lbdate = newdate.getFullYear() + "-" + month + "-" + day;

    let query1 = "select * from student_detail where clerk_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.clerkid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        con.query("select * from program_master", function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            con.query("select * from dep_master", function (err3, result3) {
                if (err3) throw err3;
                dep_items = JSON.parse(JSON.stringify(result3));
                con.query("select * from division_detail", function (err4, result4) {
                    if (err4) throw err4;
                    div_items = JSON.parse(JSON.stringify(result4));
                    res.render("clerk/clerk_manage_student", {
                        items: items,
                        pitems: prog_items,
                        ditems: dep_items,
                        divitems: div_items,
                        lbdate: lbdate,
                        layout: 'layouts/clerklayouts'
                    });
                })
            })
        })

    })
});

app.post("/filldropdown", function (req, res) {
    const { sem, pid } = req.body;

    con.query("select * from division_detail where program_id=? and semester=?", [pid, sem], function (err, result) {
        var data = "<option value=0>--Select Division--</option>"
        for (var i = 0; i < result.length; i++) {
            data += "<option value=" + result[i].div_id + ">" + result[i].division + "</option>";
        }
        console.log(data);
        res.send(data);
    });

})


app.post("/add_student", function (req, res) {
    const { txtrno, txtname, txtadd, txtcity, txtmno, gender, txtemail, txtdob, selprogram, txtcname, selsem, seldiv } = req.body;
    let query1 = "select max(stud_id) as stud_id from student_detail";
    sess = req.session;
    con.query(query1, (err, result) => {
        var sid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                sid = row.stud_id + 1;
            } else {
                sid = 1;
            }

            let query2 = "insert into student_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [sid, txtrno, txtname, txtadd, txtcity, txtmno, txtemail, txtdob, gender, selprogram, txtcname, selsem, seldiv, sess.clerkid], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('student Inserted Succesfully'); window.location.href='/clerk_manage_student'; </script>")
            })
        }

    })
});



app.get("/clerk_edit_student/:sid", function (req, res) {
    let query1 = "select stud_id,rno,stud_name,address,city,mobile_no,email_id,date_format(dob,'%Y-%m-%d') as dob,gender,program_id,class_name,semester,div_id,clerk_id from student_detail where stud_id=?";
    let items = [];
    con.query(query1, [req.params.sid], (err, result1) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        console.log(items[0].dob);
        con.query("select * from program_master", function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));

            con.query("select * from division_detail", function (err4, result4) {
                if (err4) throw err4;
                div_items = JSON.parse(JSON.stringify(result4));
                res.render("clerk/clerk_edit_student", {
                    items: items,
                    pitems: prog_items,
                    divitems: div_items,
                    layout: 'layouts/clerklayouts'
                });
            })
        })
    });
});


app.post("/update_student", function (req, res) {
    const { txtrno, txtname, txtadd, txtcity, txtmno, gender, txtemail, txtdob, selprogram, txtcname, selsem, seldiv, txtsid } = req.body;
    let query1 = "update student_detail set rno=?,stud_name=?,address=?,city=?,mobile_no=?,email_id=?,dob=?,gender=?,program_id=?,class_name=?,semester=?,div_id=? where stud_id=?";
    con.query(query1, [txtrno, txtname, txtadd, txtcity, txtmno, txtemail, txtdob, gender, selprogram, txtcname, selsem, seldiv, txtsid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Student Updated Succesfully'); window.location.href='/clerk_manage_student'; </script>")
    });
})

//clerk manage student coding end...



//hod coding start..............

//hod manage subject coding start...
app.get("/hod_manage_subject", function (req, res) {



    let query1 = "select * from subject_master where hod_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.hodid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        con.query("select * from program_master where dep_id=(select dep_id from hod_detail where hod_id=?)", [sess.hodid], function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("hod/hod_manage_subject", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/hodlayouts'
            })
        })

    })
});



app.post("/add_subject", function (req, res) {
    const { txtname, selprogram, txtcode, selsem } = req.body;
    let query1 = "select max(sub_id) as sub_id from subject_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var sid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                sid = row.sub_id + 1;
            } else {
                sid = 1;
            }

            let query2 = "insert into subject_master values(?,?,?,?,?,?,?)";
            con.query(query2, [sid, txtname, selprogram, txtcode, selsem, sess.hodid, 0], function (err, result2) {
                if (err) throw err;
                res.write("<script>alert('Subject Inserted Succesfully'); window.location.href='/hod_manage_subject'; </script>")
            })
        }

    })
});


app.get("/inactive_subject/:sid", function (req, res) {

    let query1 = "update subject_master set sub_status=? where sub_id=?";
    con.query(query1, [1, req.params.sid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Subject Inactive Succesfully'); window.location.href='/hod_manage_subject'; </script>")
    });
});

app.get("/active_subject/:sid", function (req, res) {

    let query1 = "update subject_master set sub_status=? where sub_id=?";
    con.query(query1, [0, req.params.sid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Subject Active Succesfully'); window.location.href='/hod_manage_subject'; </script>")
    });
});

app.get("/hod_edit_subject/:sid", function (req, res) {
    let query1 = "select * from subject_master where sub_id=?";
    let items = [];
    sess = req.session;
    con.query(query1, [req.params.sid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        console.log(items[0]);
        con.query("select * from program_master where dep_id=(select dep_id from hod_detail where hod_id=?)", [sess.hodid], function (err1, result2) {
            prog_items = JSON.parse(JSON.stringify(result2));
            res.render("hod/hod_edit_subject", {
                items: items,
                pitems: prog_items,
                layout: 'layouts/hodlayouts',
            });
        });
    });
});

app.post("/update_subject", function (req, res) {
    const { txtname, selprogram, txtcode, selsem, txtsid } = req.body;
    let query1 = "update subject_master set sub_name=?,program_id=?,sub_code=?,semester=? where sub_id=?";
    con.query(query1, [txtname, selprogram, txtcode, selsem, txtsid], (err, result1) => {
        if (err) throw err;
        res.write("<script>alert('Subject Updated Succesfully'); window.location.href='/hod_manage_subject'; </script>")
    });
})
//hod manage subject coding end...


//hod change pwd coding start...
app.get("/hod_change_pwd", function (req, res) {
    res.render("hod/hod_change_pwd", { layout: 'layouts/hodlayouts' })
});


app.post("/change_pwd", function (req, res) {
    const { txtopwd, txtnpwd, txtcpwd } = req.body;
    var query1 = "select pwd from hod_detail where hod_id=?";
    sess = req.session;
    con.query(query1, [sess.hodid], (err, result) => {

        var row = JSON.parse(JSON.stringify(result[0]));
        if (txtopwd == row.pwd) {
            let query1 = "update hod_detail set pwd=? where hod_id=?";
            con.query(query1, [txtnpwd, sess.hodid], (err, result1) => {
                if (err) throw err;
                res.write("<script>alert('Password Updated Succesfully'); window.location.href='/hod_change_pwd'; </script>")
            });
        } else {
            res.write("<script>alert('Old Password Is Invalid'); window.location.href='/hod_change_pwd'; </script>")
        }
    })

})
//hod change pwd coding end...


//hod assign subject coding start
app.get("/hod_assign_subject", function (req, res) {

    let query1 = "select * from sub_assign_master where hod_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.hodid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        con.query("select * from program_master where dep_id=(select dep_id from hod_detail where hod_id=?)", [sess.hodid], function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            con.query("select * from division_detail", function (err3, result3) {
                if (err3) throw err3;
                div_items = JSON.parse(JSON.stringify(result3));
                con.query("select * from subject_master where hod_id=?", [sess.hodid], function (err4, result4) {
                    if (err4) throw err4;
                    sub_items = JSON.parse(JSON.stringify(result4));
                    res.render("hod/hod_assign_subject", {
                        items: items,
                        pitems: prog_items,
                        divitems: div_items,
                        subitems: sub_items,
                        layout: 'layouts/hodlayouts'
                    })
                })
            })
        })
    })
});


app.post("/filldropdown_subject", function (req, res) {
    const { sem, pid } = req.body;

    con.query("select * from subject_master where program_id=? and semester=?", [pid, sem], function (err, result) {
        var data = "<option value=0>--Select Subject--</option>"
        for (var i = 0; i < result.length; i++) {
            data += "<option value=" + result[i].sub_id + ">" + result[i].sub_name + "</option>";
        }
        console.log(data);
        res.send(data);
    });

})

app.post("/add_assign_subject", function (req, res) {
    const { txtayear, selprogram, selsem, seldiv, selsub } = req.body;
    sess = req.session;
    con.query("select * from sub_assign_master where assign_year=? and sub_id=? and hod_id=?", [txtayear, selsub, sess.hodid], function (err3, result3) {
        if (result3.length > 0) {
            res.write("<script>alert('Faculty Already Assigned To This Subject'); window.location.href='/hod_assign_subject'; </script>")
        } else {
            let query1 = "select max(assign_id) as assign_id from sub_assign_master";
            sess = req.session;
            con.query(query1, (err, result) => {
                var aid = 0;
                if (err) {
                    throw err;
                } else {

                    if (result.length > 0) {
                        var row = JSON.parse(JSON.stringify(result[0]));
                        aid = row.assign_id + 1;
                    } else {
                        aid = 1;
                    }

                    let query2 = "insert into sub_assign_master values(?,?,?,?,?,?,?)";
                    con.query(query2, [aid, txtayear, selsub, sess.hodid, selprogram, selsem, seldiv, 0], function (err, result2) {
                        if (err) throw err;
                        res.write("<script>alert('Subject Assign Succesfully'); window.location.href='/hod_assign_subject'; </script>")
                    })
                }

            })
        }
    })


});


app.get("/hod_assign_subject_faculty/:aid", function (req, res) {

    let query1 = "select * from faculty_detail where program_id in (select program_id from program_master where dep_id=(select dep_id from hod_detail where hod_id=?))";
    let items = [];
    sess = req.session;
    con.query("select * from sub_assign_detail where assign_id=?", [req.params.aid], function (err, result) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        con.query(query1, [sess.hodid], function (err, result1) {
            if (err) throw err;
            fitems = JSON.parse(JSON.stringify(result1));



            con.query("select * from subject_master where sub_id=(select sub_id from sub_assign_master where assign_id=?)", [req.params.aid], function (err4, result4) {
                if (err4) throw err4;
                sub_items = JSON.parse(JSON.stringify(result4));

                res.render("hod/hod_assign_subject_faculty", {
                    items: items,
                    fitems: fitems,
                    divitems: div_items,
                    subitems: sub_items,
                    aid: req.params.aid,
                    layout: 'layouts/hodlayouts'
                })
            })


        })
    })
});


app.post("/add_sub_faculty", function (req, res) {
    const { txtsname, selfaculty, txtaid } = req.body;

    let query2 = "insert into sub_assign_detail values(?,?)";
    con.query(query2, [txtaid, selfaculty], function (err, result2) {
        if (err) throw err;
        res.write("<script>alert('Faculty Assign To Subject Succesfully'); window.location.href='/hod_assign_subject_faculty/" + txtaid + "'; </script>")
    })



});

app.get("/remove_faculty_subject/:aid/:fid", function (req, res) {
    let query2 = "delete from sub_assign_detail where assign_id=? and faculty_id=?";
    con.query(query2, [req.params.aid, req.params.fid], function (err, result2) {
        if (err) throw err;
        res.write("<script>alert('Faculty Remove From Subject Succesfully'); window.location.href='/hod_assign_subject_faculty/" + req.params.aid + "'; </script>")
    })
});

app.get("/delete_assign_subject/:aid", function (req, res) {
    let query2 = "delete from sub_assign_detail where assign_id=?";
    con.query(query2, [req.params.aid], function (err, result2) {
        if (err) throw err;
        con.query("delete from sub_assign_master where assign_id=?", [req.params.aid], function (err2, result2) {
            if (err2) throw err2;
            res.write("<script>alert('Faculty Remove From Subject Succesfully'); window.location.href='/hod_assign_subject'; </script>");
        })

    })
});
//hod assign subject coding end




//hod coding end............





//faculty coding start...............
//faculty manage attendance coding start...

//faculty manage attendance coding end...
app.get("/faculty_manage_attendance", function (req, res) {



    let query1 = "select * from sub_assign_master where hod_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.hodid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        con.query("select * from program_master where program_id=(select program_id from faculty_detail where faculty_id=?)", [sess.fid], function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            con.query("select * from division_detail", function (err3, result3) {
                if (err3) throw err3;
                div_items = JSON.parse(JSON.stringify(result3));
                con.query("select * from subject_master where sub_id in (select sub_id from sub_assign_master where assign_id in (select assign_id from sub_assign_detail where faculty_id=?))", [sess.fid], function (err4, result4) {
                    
                    if (err4) throw err4;
                    sub_items = JSON.parse(JSON.stringify(result4));
                    con.query("select * from attend_master where faculty_id=?",[sess.fid],function(err5,result5){
                        if(err5) throw err5;
                        attend_items = JSON.parse(JSON.stringify(result5));
                        res.render("faculty/faculty_manage_attendance", {
                            items: items,
                            pitems: prog_items,
                            divitems: div_items,
                            subitems: sub_items,
                            aitems: attend_items,
                            layout: 'layouts/facultylayouts'
                        })
                    })
                })
            })
        })
    })
});


app.post("/add_attendance", function (req, res) {
    const { sellno, selprogram, selsem, seldiv, selsub, txtltime } = req.body;
    sess = req.session;
    var newdate = new Date();


    var adate = newdate.getFullYear() + "-" + newdate.getMonth() + "-" + newdate.getDate();

    let query1 = "select max(attend_id) as attend_id from attend_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var aid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                aid = row.attend_id + 1;
            } else {
                aid = 1;
            }

            let query2 = "insert into attend_master values(?,?,?,?,?,?,?,?,?)";
            con.query(query2, [aid, adate, sess.fid,sellno, selprogram,selsub, selsem,txtltime, seldiv], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?",[selprogram,selsem,seldiv],function(err3,result3){
                    for(var i = 0; i<result3.length; i++){              
                        con.query("insert into attend_detail values(?,?,?,?)",[aid,result3[i].stud_id,result3[i].rno,"P"],function(err4,result4){

                        })
                    }
                })
                res.write("<script>alert('Attendance Added  Succesfully'); window.location.href='/faculty_manage_attendance_detail/"+aid+"'; </script>")
            })
        }

    })



});


app.get("/faculty_manage_attendance_detail/:aid", function (req, res) {

    let query1 = "select * from attend_detail where attend_id=?";
    let items = [];
    let items2 = [];
    sess = req.session;
    con.query("select * from attend_master where attend_id=?", [req.params.aid], function (err, result1) {
        if (err) throw err;
        items2 = JSON.parse(JSON.stringify(result1[0]));
    });
    con.query(query1, [req.params.aid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

       console.log(items2);
            con.query("select * from student_detail where program_id=? and div_id=? and semester=?",[items2.program_id,items2.div_id,items2.semester], function (err3, result3) {
                if (err3) throw err3;
                stud_items = JSON.parse(JSON.stringify(result3));
                console.log(stud_items)
                    res.render("faculty/faculty_manage_attendance_detail", {
                        items: items,
                        sitems: stud_items,
                        
                        layout: 'layouts/facultylayouts'
                    })
             
            })
       
    })
});

app.get("/absent_student/:aid/:sid", function (req, res) {
    let query2 = "update attend_detail set present_absent=? where attend_id=? and stud_id=?";
    con.query(query2, ['A',req.params.aid, req.params.sid], function (err, result2) {
        if (err) throw err;
        res.write("<script>alert('Student Absent Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
    })
});


app.get("/present_student/:aid/:sid", function (req, res) {
    let query2 = "update attend_detail set present_absent=? where attend_id=? and stud_id=?";
    con.query(query2, ['P',req.params.aid, req.params.sid], function (err, result2) {
        if (err) throw err;
        res.write("<script>alert('Student Present Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
    })
});


app.get("/faculty_delete_attendance/:aid", function (req, res) {
    let query2 = "delete from attend_detail where attend_id=?";
    con.query(query2, [req.params.aid], function (err, result2) {
        if (err) throw err;
        con.query("delete from attend_master where attend_id=?", [req.params.aid], function (err2, result2) {
            if (err2) throw err2;
            res.write("<script>alert('Attendance Deleted Succesfully'); window.location.href='/faculty_manage_attendance'; </script>");
        })

    })
});


//faculty change pwd coding start...
app.get("/faculty_change_pwd", function (req, res) {
    res.render("faculty/faculty_change_pwd", { layout: 'layouts/facultylayouts' })
});


app.post("/faculty_update_pwd", function (req, res) {
    const { txtopwd, txtnpwd, txtcpwd } = req.body;
    var query1 = "select pwd from faculty_detail where faculty_id=?";
    sess = req.session;
    con.query(query1, [sess.fid], (err, result) => {

        var row = JSON.parse(JSON.stringify(result[0]));
        if (txtopwd == row.pwd) {
            let query1 = "update faculty_detail set pwd=? where faculty_id=?";
            con.query(query1, [txtnpwd, sess.fid], (err, result1) => {
                if (err) throw err;
                res.write("<script>alert('Password Updated Succesfully'); window.location.href='/faculty_change_pwd'; </script>")
            });
        } else {
            res.write("<script>alert('Old Password Is Invalid'); window.location.href='/faculty_change_pwd'; </script>")
        }
    })

})
//faculty change pwd coding end...



//faculty manage internal Marks start.....
app.get("/faculty_manage_internal_marks", function (req, res) {



    let query1 = "select * from internal_marks_master where faculty_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.fid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        con.query("select * from program_master where program_id=(select program_id from faculty_detail where faculty_id=?)", [sess.fid], function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            con.query("select * from division_detail", function (err3, result3) {
                if (err3) throw err3;
                div_items = JSON.parse(JSON.stringify(result3));
                con.query("select * from subject_master where sub_id in (select sub_id from sub_assign_master where assign_id in (select assign_id from sub_assign_detail where faculty_id=?))", [sess.fid], function (err4, result4) {
                    
                    if (err4) throw err4;
                    sub_items = JSON.parse(JSON.stringify(result4));
                  
                        res.render("faculty/faculty_manage_internal_marks", {
                            items: items,
                            pitems: prog_items,
                            divitems: div_items,
                            subitems: sub_items,
                            
                            layout: 'layouts/facultylayouts'
                        })
                    
                })
            })
        })
    })
});


app.post("/faculty_add_internal_marks", function (req, res) {
    const { seleno, selprogram, selsem, seldiv, selsub, txttmark,txtayear } = req.body;
    sess = req.session;
    var newdate = new Date();


    var adate = newdate.getFullYear() + "-" + newdate.getMonth() + "-" + newdate.getDate();

    let query1 = "select max(marks_id) as marks_id from internal_marks_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var mid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                mid = row.marks_id + 1;
            } else {
                mid = 1;
            }

            let query2 = "insert into internal_marks_master values(?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [mid, adate, selprogram, selsem, selsub,sess.fid,seleno,txttmark,txtayear, seldiv], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?",[selprogram,selsem,seldiv],function(err3,result3){
                    for(var i = 0; i<result3.length; i++){              
                        con.query("insert into internal_marks_detail values(?,?,?)",[mid,result3[i].stud_id,0],function(err4,result4){

                        })
                    }
                })
                res.write("<script>alert('Marks Added  Succesfully'); window.location.href='/faculty_manage_internal_marks'; </script>")
            })
        }
    })
});

app.get("/faculty_manage_internal_marks_detail/:mid", function (req, res) {

    let query1 = "select * from internal_marks_detail where marks_id=?";
    let items = [];
    let items2 = [];
    sess = req.session;
    con.query("select * from internal_marks_master where marks_id=?", [req.params.mid], function (err, result1) {
        if (err) throw err;
        items2 = JSON.parse(JSON.stringify(result1[0]));
    });
    con.query(query1, [req.params.mid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

       console.log(items2);
            con.query("select * from student_detail where program_id=? and div_id=? and semester=?",[items2.program_id,items2.div_id,items2.semester], function (err3, result3) {
                if (err3) throw err3;
                stud_items = JSON.parse(JSON.stringify(result3));
                //console.log(stud_items)
                    res.render("faculty/faculty_manage_internal_marks_detail", {
                        items: items,
                        sitems: stud_items,
                        mid: req.params.mid,
                        layout: 'layouts/facultylayouts'
                    })
             
            })
       
    })
});



app.post("/faculty_update_internal_marks", function (req, res) {
    console.log("hello");
    var mid = req.body.txtmarkid;
    con.query("select * from internal_marks_detail where marks_id=?",[mid],function(err3,result3){
        for(var i = 0; i<result3.length; i++){              
            var sid= req.body.txtstudid[i]
            
            var omarks= req.body.txtomark[i]
            
            con.query("update internal_marks_detail set marks=? where marks_id=? and stud_id=?",[omarks,mid,sid],function(err4,result4){

            })
        }

    })
    
    res.write("<script>alert('Marks Update  Succesfully'); window.location.href='/faculty_manage_internal_marks'; </script>")
   
    
});

app.get("/faculty_delete_internal_marks_detail/:mid", function (req, res) {
    let query2 = "delete from internal_marks_detail where marks_id=?";
    con.query(query2, [req.params.mid], function (err, result2) {
        if (err) throw err;
        con.query("delete from internal_marks_master where marks_id=?", [req.params.mid], function (err2, result2) {
            if (err2) throw err2;
            res.write("<script>alert('Marks Deleted Succesfully'); window.location.href='/faculty_manage_internal_marks'; </script>");
        })

    })
});

//faculty manage internal marks coding end...




//faculty manage assigmnet marks detail coding start...
app.get("/faculty_manage_assign_detail", function (req, res) {



    let query1 = "select * from assign_master where faculty_id=?";
    let items = [];
    sess = req.session;

    con.query(query1, [sess.fid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        con.query("select * from program_master where program_id=(select program_id from faculty_detail where faculty_id=?)", [sess.fid], function (err1, result2) {
            if (err1) throw err1;
            prog_items = JSON.parse(JSON.stringify(result2));
            con.query("select * from division_detail", function (err3, result3) {
                if (err3) throw err3;
                div_items = JSON.parse(JSON.stringify(result3));
                con.query("select * from subject_master where sub_id in (select sub_id from sub_assign_master where assign_id in (select assign_id from sub_assign_detail where faculty_id=?))", [sess.fid], function (err4, result4) {
                    
                    if (err4) throw err4;
                    sub_items = JSON.parse(JSON.stringify(result4));
                  
                        res.render("faculty/faculty_manage_assign_detail", {
                            items: items,
                            pitems: prog_items,
                            divitems: div_items,
                            subitems: sub_items,
                            
                            layout: 'layouts/facultylayouts'
                        })
                    
                })
            })
        })
    })
});
app.post("/faculty_add_assign_marks", function (req, res) {
    const { selprogram, selsem, seldiv, selsub, txtno } = req.body;
    sess = req.session;
    
    let query1 = "select max(assign_id) as assign_id from assign_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var aid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                aid = row.assign_id + 1;
            } else {
                aid = 1;
            }

            let query2 = "insert into assign_master values(?,?,?,?,?,?,?)";
            con.query(query2, [aid, selprogram,sess.fid, selsub,selsem,seldiv,txtno], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?",[selprogram,selsem,seldiv],function(err3,result3){
                    for(var i = 0; i<result3.length; i++){           
                        for(var j=0; j<parseInt(txtno);j++)
                        {   
                            con.query("insert into assign_detail(assign_id,stud_id,assign_no,assign_received,marks) values(?,?,?,?,?)",[aid,result3[i].stud_id,j+1,"N",0],function(err4,result4){

                            })
                        }
                    }
                })
                res.write("<script>alert('Assingment Added  Succesfully'); window.location.href='/faculty_manage_assign_detail'; </script>")
            })
        }
    })
});


app.get("/faculty_manage_assign_marks_detail/:aid", function (req, res) {

    let query1 = "select * from assign_detail where assign_id=?";
    let items = [];
    let items2 = [];
    sess = req.session;
    con.query("select * from assign_master where assign_id=?", [req.params.aid], function (err, result1) {
        if (err) throw err;
        items2 = JSON.parse(JSON.stringify(result1[0]));
    });
    con.query(query1, [req.params.aid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

       console.log(items2);
            con.query("select * from student_detail where program_id=? and div_id=? and semester=?",[items2.program_id,items2.div_id,items2.semester], function (err3, result3) {
                if (err3) throw err3;
                stud_items = JSON.parse(JSON.stringify(result3));
               // console.log(stud_items)
                    res.render("faculty/faculty_manage_assign_marks_detail", {
                        items: items,
                        sitems: stud_items,
                        aid: req.params.aid,
                        layout: 'layouts/facultylayouts'
                    })
             
            })
       
    })
});


app.post("/faculty_update_assignment_marks", function (req, res) {
    //console.log("hello");
    var aid = req.body.txtaid;
    
    con.query("select * from assign_detail where assign_id=?",[aid],function(err3,result3){
        for(var i = 0; i<result3.length; i++){              
            var sid= req.body.txtstudid[i]
            var ano= req.body.txtano[i]
           
            var ar= req.body.selar[i]
            var omarks= req.body.txtomark[i]

         
            con.query("update assign_detail set assign_received=?,marks=? where assign_id=? and stud_id=? and assign_no=?",[ar,omarks,aid,sid,ano],function(err4,result4){

            })
            //console.log(sid+" "+ano+" "+ar+" "+omarks);
            
          }

    })
    
    res.write("<script>alert('Marks Update Succesfully'); window.location.href='/faculty_manage_assign_detail'; </script>")
   
    
});


app.get("/faculty_delete_assign_detail/:aid", function (req, res) {
    let query2 = "delete from assign_detail where assign_id=?";
    con.query(query2, [req.params.aid], function (err, result2) {
        if (err) throw err;
        con.query("delete from assign_master where assign_id=?", [req.params.aid], function (err2, result2) {
            if (err2) throw err2;
            res.write("<script>alert('Assignment Deleted Succesfully'); window.location.href='/faculty_manage_assign_detail'; </script>");
        })

    })
});
//faculty manage assigmnet marks detail coding end...
*/
//faculty coding end...............
app.post("/loginapi", function (req, res) {
    const { txtemail, txtpwd, selltype } = req.body;
    if (selltype == "1") {
        var query1 = "select * from principal_detail where email_id=? and pwd=? and status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                var pid = row.principal_id;
                res.send({ result: "Principal Login Successfull", "pid": pid });
                //res.write("<script>alert('Principal Login Succesfully'); window.location.href='/principal_manage_dep'; </script>")
            } else {
                res.send({ result: "Check Your Email id or Password" });
            }
        })
    } else if (selltype == "2") {

    } else if (selltype == "3") {
        var query1 = "select * from hod_detail where email_id=? and pwd=? and hod_status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                var hodid = row.hod_id;
                res.send({ result: "Hod Login Successfull", "hodid": hodid });

            } else {
                res.send({ result: "Check Your Email id or Password" });
            }
        })
    } else if (selltype == "4") {
        var query1 = "select * from faculty_detail where email_id=? and pwd=? and faculty_status=?";
        con.query(query1, [txtemail, txtpwd, 0], function (err, result) {
            if (err) throw err;
            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                var fid = row.faculty_id;
                res.send({ result: "Faculty Login Successfull", "fid": fid });

            } else {
                res.send({ result: "Check Your Email id or Password" });
            }
        })
    }
})



//principal manage department coding start....
app.get("/principal_fetch_dept_detail", function (req, res) {
    let query1 = "select * from dep_master";
    let items = [];
    con.query(query1, (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        //console.log(items[0]);
        res.send({ status: "success", ditems: items });
    });

})

app.post("/add_dept_api", function (req, res) {
    const { name } = req.body;
    let query1 = "select max(dep_id) as dep_id from dep_master";

    con.query(query1, (err, result) => {
        var depid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                depid = row.dep_id + 1;
            } else {
                depid = 1;
            }

            let query2 = "insert into dep_master values(?,?,?)";
            con.query(query2, [depid, name, 0], function (err, result2) {
                if (err) throw err;
                res.send({ result: "Department Added Successfully" });
            });
        }

    })
});

app.post("/inactive_dept", function (req, res) {
    const { did } = req.body;
    let query1 = "update dep_master set dep_status=? where dep_id=?";
    con.query(query1, [1, did], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Department Inactive Successfully" });
    });
});



app.post("/active_dept", function (req, res) {
    const { did } = req.body;
    let query1 = "update dep_master set dep_status=? where dep_id=?";
    con.query(query1, [0, did], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Department Active Successfully" });
    });
});



app.post("/update_dept_api", function (req, res) {
    const { did, name } = req.body;
    let query1 = "update dep_master set dep_name=? where dep_id=?";
    con.query(query1, [name, did], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Department Updated Successfully" });
    });
})


//principal manage department coding end....



//Principal manage hod coding start....

app.post("/add_hod_api", function (req, res) {
    const { name, add, city, mno, email, pwd, did, sque, sans, gender } = req.body;
    let query1 = "select max(hod_id) as hod_id from hod_detail";

    con.query(query1, (err, result) => {
        var hodid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                hodid = row.hod_id + 1;
            } else {
                hodid = 1;
            }

            let query2 = "insert into hod_detail values(?,?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [hodid, name, add, city, mno, email, pwd, gender, sque, sans, did, 0], function (err, result2) {
                if (err) throw err;
                res.send({ result: "HOD Added Successfully" });
            })
        }

    })
});


app.get("/principal_fetch_hod_detail", function (req, res) {
    let query1 = "select hod_id,hod_name,address,city,mobile_no,email_id,pwd,gender,security_que,security_ans,h.dep_id dep_id,dep_name,hod_status from hod_detail h,dep_master d where h.dep_id=d.dep_id";
    let items = [];
    con.query(query1, function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        //console.log(items[0]);
        res.send({ status: "success", hitems: items });
    })
});

app.post("/inactive_hod", function (req, res) {
    const { hodid } = req.body;
    let query1 = "update hod_detail set hod_status=? where hod_id=?";
    con.query(query1, [1, hodid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Hod Inactive Successfully" });
    });
});

app.post("/active_hod", function (req, res) {
    const { hodid } = req.body;
    let query1 = "update hod_detail set hod_status=? where hod_id=?";
    con.query(query1, [0, hodid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Hod Active Successfully" });
    });
});

app.post("/update_hod_api", function (req, res) {
    const { hodid, name, add, city, mno, email, pwd, did, sque, sans, gender } = req.body;
    let query1 = "update hod_detail set hod_name=?,address=?,city=?,mobile_no=?,email_id=?,pwd=?,gender=?,security_que=?,security_ans=?,dep_id=? where hod_id=?";
    con.query(query1, [name, add, city, mno, email, pwd, gender, sque, sans, did, hodid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Hod Updated Successfully" });
    });
})

//Principal manage hod coding end....


//principal manage clerk coding start...

app.post("/add_clerk_api", function (req, res) {
    const { name, add, city, mno, email, pwd, sque, sans, gender } = req.body;
    let query1 = "select max(clerk_id) as clerk_id from clerk_detail";

    con.query(query1, (err, result) => {
        var clerkid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                clerkid = row.clerk_id + 1;
            } else {
                clerkid = 1;
            }

            let query2 = "insert into clerk_detail values(?,?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [clerkid, name, add, city, mno, email, pwd, gender, sque, sans, 0], function (err, result2) {
                if (err) throw err;
                res.send({ result: "Clerk Added Successfully" });
            })
        }

    })
});


app.get("/principal_fetch_clerk_detail", function (req, res) {
    let query1 = "select * from clerk_detail";
    let items = [];
    con.query(query1, function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        //console.log(items[0]);
        res.send({ status: "success", citems: items });
    })
});

app.post("/inactive_clerk", function (req, res) {
    const { clerkid } = req.body;
    let query1 = "update clerk_detail set clerk_status=? where clerk_id=?";
    con.query(query1, [1, clerkid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Clerk Inactive Successfully" });
    });
});


app.post("/active_clerk", function (req, res) {
    const { clerkid } = req.body;
    let query1 = "update clerk_detail set clerk_status=? where clerk_id=?";
    con.query(query1, [0, clerkid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Clerk Active Successfully" });
    });
});


app.post("/update_clerk_api", function (req, res) {
    const { clerkid, name, add, city, mno, email, pwd, sque, sans, gender } = req.body;
    let query1 = "update clerk_detail set clerk_name=?,address=?,city=?,mobile_no=?,email_id=?,pwd=?,gender=?,security_que=?,security_ans=? where clerk_id=?";
    con.query(query1, [name, add, city, mno, email, pwd, gender, sque, sans, clerkid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Clerk Updated Successfully" });
    });
})
//principal manage clerk coding end...



//hod manage subject api start....

app.get("/fetch_program_api/:hodid", function (req, res) {
    let query1 = "select * from program_master where dep_id=(select dep_id from hod_detail where hod_id=?)";
    let items = [];
    con.query(query1, [req.params.hodid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        //console.log(items[0]);
        res.send({ status: "success", pitems: items });
    });

})



app.post("/add_subject_api", function (req, res) {
    const { name, scode, progid, sem, hodid } = req.body;
    let query1 = "select max(sub_id) as sub_id from subject_master";
    con.query(query1, (err, result) => {
        var subid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                subid = row.sub_id + 1;
            } else {
                subid = 1;
            }

            let query2 = "insert into subject_master values(?,?,?,?,?,?,?)";
            con.query(query2, [subid, name, progid, scode, sem, hodid, 0], function (err, result2) {
                if (err) throw err;
                res.send({ result: "Subject Added Successfully" });
            })
        }
    })
});


app.get("/hod_fetch_subject_detail/:hodid", function (req, res) {
    let query1 = "select sub_id,sub_name,s.program_id as program_id,program_name,sub_code,semester,hod_id,sub_status from subject_master s,program_master p where hod_id=? and s.program_id=p.program_id";
    let items = [];
    con.query(query1, [req.params.hodid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        //console.log(items[0]);
        res.send({ status: "success", sitems: items });
    })
});


app.post("/inactive_subject", function (req, res) {
    const { subid } = req.body;
    let query1 = "update subject_master set sub_status=? where sub_id=?";
    con.query(query1, [1, subid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Subject Inactive Successfully" });
    });
});


app.post("/active_subject", function (req, res) {
    const { subid } = req.body;
    let query1 = "update subject_master set sub_status=? where sub_id=?";
    con.query(query1, [0, subid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Subject Active Successfully" });
    });
});

app.post("/update_subject_api", function (req, res) {
    const { name, scode, progid, sem, hodid, subid } = req.body;
    let query1 = "update subject_master set sub_name=?,program_id=?,sub_code=?,semester=? where sub_id=?";
    con.query(query1, [name, progid, scode, sem, subid], (err, result1) => {
        if (err) throw err;
        res.send({ result: "Subject Updated Successfully" });
    });
})




//hod manage subject api end....

//hod change pwd coding api start...

app.post("/hod_change_pwd", function (req, res) {
    const { opwd, npwd, cpwd, hodid } = req.body;
    var query1 = "select pwd from hod_detail where hod_id=?";

    con.query(query1, [hodid], (err, result) => {

        var row = JSON.parse(JSON.stringify(result[0]));
        if (opwd == row.pwd) {
            let query1 = "update hod_detail set pwd=? where hod_id=?";
            con.query(query1, [npwd, hodid], (err, result1) => {
                if (err) throw err;
                res.send({ result: "Password Updated Successfully" });
            });
        } else {
            res.send({ result: "Old Password Is Invalid" });
        }
    })

})
//hod change pwd coding api end...





//Faculty Coding Start:-

//faculty change pwd coding api start...

app.post("/faculty_change_pwd", function (req, res) {
    const { opwd, npwd, cpwd, fid } = req.body;
    var query1 = "select pwd from faculty_detail where faculty_id=?";

    con.query(query1, [fid], (err, result) => {

        var row = JSON.parse(JSON.stringify(result[0]));
        if (opwd == row.pwd) {
            let query1 = "update faculty_detail set pwd=? where faculty_id=?";
            con.query(query1, [npwd, fid], (err, result1) => {
                if (err) throw err;
                res.send({ result: "Password Updated Successfully" });
            });
        } else {
            res.send({ result: "Old Password Is Invalid" });
        }
    })

})
//Faculty change pwd coding api end...

app.get("/faculty_fetch_program_api/:fid", function (req, res) {
    let query1 = "select * from program_master where program_id=(select program_id from faculty_detail where faculty_id=?)";
    let items = [];
    con.query(query1, [req.params.fid], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));
        //console.log(items[0]);
        res.send({ status: "success", pitems: items });
    });

})

app.get("/faculty_fetch_div_sub_api/:pid/:sem/:fid", function (req, res) {
    let query1 = "select * from division_detail where program_id=? and semester=?";
    let items = [];
    let items2 = [];
    con.query(query1, [req.params.pid, req.params.sem], (err, result) => {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result));

        //console.log(items[0]);
        let query1 = "select * from subject_master where sub_id in (select sub_id from sub_assign_master where assign_id in (select assign_id from sub_assign_detail where faculty_id=?))";
        con.query(query1, [req.params.fid], (err, result2) => {
            if (err) throw err;
            items2 = JSON.parse(JSON.stringify(result2));

            //console.log(items[0]);
            res.send({ status: "success", pitems: items, sitems: items2 });
        });
    });
})



//faculty add attendance api coding start...
app.post("/add_attendance_api", function (req, res) {
    const { lno, progid, sem, divid, subid, fid, ltime } = req.body;
    sess = req.session;
    var newdate = new Date();


    var adate = newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate();

    let query1 = "select max(attend_id) as attend_id from attend_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var aid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                aid = row.attend_id + 1;
            } else {
                aid = 1;
            }

            let query2 = "insert into attend_master values(?,?,?,?,?,?,?,?,?)";
            con.query(query2, [aid, adate, fid, lno, progid, subid, sem, ltime, divid], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?", [progid, sem, divid], function (err3, result3) {
                    for (var i = 0; i < result3.length; i++) {
                        con.query("insert into attend_detail values(?,?,?,?)", [aid, result3[i].stud_id, result3[i].rno, "P"], function (err4, result4) {

                        })
                    }
                })
                res.send({ status: "Attendance Added Succesfully", aid: aid })
            })
        }
    })
});


app.get("/faculty_view_student_attendance_detail_api/:aid", function (req, res) {

    let query1 = "select attend_id,a.stud_id studid,a.rno rno,present_absent,stud_name from attend_detail a,student_detail s where attend_id=? and a.stud_id=s.stud_id";
    let items = [];
    let items2 = [];

    con.query("select * from attend_master where attend_id=?", [req.params.aid], function (err, result1) {
        if (err) throw err;
        items2 = JSON.parse(JSON.stringify(result1[0]));
    });
    con.query(query1, [req.params.aid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));

        console.log(items2);

        
        res.send({ status: "Success", items: items })

    })
});


app.post("/absent_student_api", function (req, res) {
    const { attendid, studid } = req.body;
    let query2 = "update attend_detail set present_absent=? where attend_id=? and stud_id=?";
    con.query(query2, ['A', attendid, studid], function (err, result2) {
        if (err) throw err;
        //res.write("<script>alert('Student Absent Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
        res.send({ result: "Student Absent Successfully" })
    })
});


app.post("/present_student_api", function (req, res) {
    const { attendid, studid } = req.body;
    let query2 = "update attend_detail set present_absent=? where attend_id=? and stud_id=?";
    con.query(query2, ['P', attendid, studid], function (err, result2) {
        if (err) throw err;
        res.send({ result: "Student Present Successfully" })
        //res.write("<script>alert('Student Present Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
    })
});

app.get("/faculty_fetch_attend_detail/:fid", function (req, res) {
    let query1 = "select attend_id,date_format(attend_date,'%d-%m-%Y') attend_date,a.faculty_id faculty_id,faculty_name,lec_no,a.program_id program_id,program_name,a.sub_id sub_id,sub_name,a.semester semester,lec_time,a.div_id div_id,division from attend_master a,faculty_detail f,program_master p,subject_master s,division_detail d where a.faculty_id=? and a.faculty_id=f.faculty_id and a.program_id=p.program_id and a.sub_id=s.sub_id and a.div_id=d.div_id";
    let items = [];
    con.query(query1, [req.params.fid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        if (err) throw err;
        res.send({ status: "success", items: items })
    })
});




//faculty add attendance api coding end...

//faculty manage internal marks api coding start...


app.post("/add_internal_marks_api", function (req, res) {
    const { eno, progid, sem, divid, subid, fid,tmarks,ayear } = req.body;
    sess = req.session;
    var newdate = new Date();


    var adate = newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate();

    let query1 = "select max(marks_id) as marks_id from internal_marks_master";
    sess = req.session;
    con.query(query1, (err, result) => {
        var mid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                mid = row.marks_id + 1;
            } else {
                mid = 1;
            }

            let query2 = "insert into internal_marks_master values(?,?,?,?,?,?,?,?,?,?)";
            con.query(query2, [mid, adate, progid, sem, subid,fid,eno,tmarks,ayear, divid], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?",[progid,sem,divid],function(err3,result3){
                    for(var i = 0; i<result3.length; i++){              
                        con.query("insert into internal_marks_detail values(?,?,?)",[mid,result3[i].stud_id,0],function(err4,result4){
                        })
                    }
                })
                res.send({ result: "Marks Added Succesfully",mid: mid  })
            })
        }
    })
});


app.get("/faculty_fetch_internal_marks_api/:fid", function (req, res) {
    let query1 = "select marks_id,date_format(marks_date,'%d-%m-%Y') marks_date,m.program_id program_id,program_name,m.semester semester,m.sub_id sub_id,sub_name,m.faculty_id faculty_id,faculty_name,internal_exam,total_marks,m.academic_year,m.div_id div_id,division from internal_marks_master m,faculty_detail f,program_master p,subject_master s,division_detail d where m.faculty_id=? and m.faculty_id=f.faculty_id and m.program_id=p.program_id and m.sub_id=s.sub_id and m.div_id=d.div_id";
    let items = [];
    con.query(query1, [req.params.fid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        if (err) throw err;
        res.send({ status: "success", items: items })
    })
});


app.get("/faculty_view_student_marks_detail_api/:mid", function (req, res) {

    let query1 = "select marks_id,m.stud_id studid,s.rno rno,marks,stud_name from internal_marks_detail m,student_detail s where marks_id=? and m.stud_id=s.stud_id";
    let items = [];
    
    con.query(query1, [req.params.mid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        res.send({ status: "Success", items: items })

    })
});

app.post("/update_student_internal_marks_api", function (req, res) {
    const { mid, studid,omarks } = req.body;
    let query2 = "update internal_marks_detail set marks=? where marks_id=? and stud_id=?";
    con.query(query2, [omarks, mid, studid], function (err, result2) {
        if (err) throw err;
        //res.write("<script>alert('Student Absent Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
        res.send({ result: "Student Marks Updated Successfully" })
    })
});
//faculty manage internal marks api coding end...



//faculty manage assignment marks api coding start...
app.post("/add_assign_marks_api", function (req, res) {
    const { progid, sem, divid, subid, fid,nassign } = req.body;
    
    
    let query1 = "select max(assign_id) as assign_id from assign_master";
    
    con.query(query1, (err, result) => {
        var aid = 0;
        if (err) {
            throw err;
        } else {

            if (result.length > 0) {
                var row = JSON.parse(JSON.stringify(result[0]));
                aid = row.assign_id + 1;
            } else {
                aid = 1;
            }

            let query2 = "insert into assign_master values(?,?,?,?,?,?,?)";
            con.query(query2, [aid, progid,fid, subid,sem,divid,nassign], function (err, result2) {
                if (err) throw err;
                con.query("select * from student_detail where program_id=? and semester=? and div_id=?",[progid,sem,divid],function(err3,result3){
                    for(var i = 0; i<result3.length; i++){           
                        for(var j=0; j<parseInt(nassign);j++)
                        {   
                            con.query("insert into assign_detail(assign_id,stud_id,assign_no,assign_received,marks) values(?,?,?,?,?)",[aid,result3[i].stud_id,j+1,"N",0],function(err4,result4){

                            })
                        }
                    }
                })
                
                res.send({ result: "Assignment Marks Added Succesfully",aid: aid  })
            })
        }
    })
});

app.get("/faculty_fetch_assign_marks_api/:fid", function (req, res) {
    let query1 = "select assign_id,a.program_id program_id,program_name,a.faculty_id faculty_id,faculty_name,a.sub_id sub_id,sub_name,a.semester semester,a.div_id div_id,division,no_of_assign from assign_master a,faculty_detail f,program_master p,subject_master s,division_detail d where a.faculty_id=? and a.faculty_id=f.faculty_id and a.program_id=p.program_id and a.sub_id=s.sub_id and a.div_id=d.div_id";
    let items = [];
    con.query(query1, [req.params.fid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        if (err) throw err;
        res.send({ status: "success", items: items })
    })
});


app.get("/faculty_view_student_assign_detail_api/:aid", function (req, res) {

    let query1 = "select assign_detail_id,assign_id,a.stud_id studid,s.rno rno,assign_no,assign_received,stud_name,marks from assign_detail a,student_detail s where assign_id=? and a.stud_id=s.stud_id";
    let items = [];
    
    con.query(query1, [req.params.aid], function (err, result1) {
        if (err) throw err;
        items = JSON.parse(JSON.stringify(result1));
        res.send({ status: "Success", items: items })

    })
});

app.post("/update_student_assignment_marks_api", function (req, res) {
    const { adid, studid,rn,omarks } = req.body;
    let query2 = "update assign_detail set assign_received=?,marks=? where assign_detail_id=?";
    con.query(query2, [rn,omarks, adid], function (err, result2) {
        if (err) throw err;
        //res.write("<script>alert('Student Absent Successfully'); window.location.href='/faculty_manage_attendance_detail/" + req.params.aid + "'; </script>")
        res.send({ result: "Student Assignment Marks Updated Successfully" })
    })
});

//faculty manage assignment marks api coding end...
app.listen(3000, function () {
    console.log("Web Server Started At Port No: 3000 http://127.0.0.1:3000/");
});