<?php
session_start();
    if($_GET['flag'] == 1){
      $val = $_GET['val'];
        $rollno = $_SESSION['rollno'];
        $xml = simplexml_load_file('users.xml');
        $users = $xml->user;
        foreach($users as $user){
            if($user->rollno == $rollno && $val == 0){
                echo "<h5 align='center'>User Information</h5>
                  <form class='form' autocomplete='off'>
                  <fieldset id='fields' disabled='true'>
                  <div class='input-field'>
                  <input disabled type='text' id='userid' value=$user->userid class='validate'/>
                  <label class='active' for='userid'>User ID</label>
                  </div>
                  <div class='input-field'>
                  <input disabled type='text' id='rollno' value=$user->rollno class='validate'/>
                  <label class='active' for='rollno'>Roll No</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='name' value=$user->name class='validate'/>
                  <label class='active' for='name'>Name</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='fname' value=$user->father class='validate'/>
                  <label class='active' for='fname'>Father Name</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='branch' value=$user->branch class='validate'/>
                  <label class='active' for='branch'>Branch</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='year' value=$user->year class='validate'/>
                  <label class='active' for='year'>Year</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='email' value=$user->email class='validate'/>
                  <label class='active' for='email'>E-Mail</label>
                  </div>
                  <div class='input-field'>
                  <input type='text' id='address' value=$user->address class='validate'/>
                  <label class='active' for='address'>Address</label>
                  </div>
                  </fieldset>
                  <br><button onclick='otp()' class='btn waves-effect waves-light' type='button' name='editbutton'>EDIT</button>
                  <button onclick='window.location.reload()' class='btn waves-effect waves-light' type='button' name='editbutton'>GO BACK</button>
                </form>";
                exit();
            } else if($user->rollno == $rollno && $val == 1){
              echo "<h5 align='center'>User Information</h5>
                <form class='form' autocomplete='off'>
                <fieldset id='fields'>
                <div class='input-field'>
                <input disabled type='text' id='userid' value=$user->userid class='validate'/>
                <label class='active' for='userid'>User ID</label>
                </div>
                <div class='input-field'>
                <input disabled type='text' id='rollno' value=$user->rollno class='validate'/>
                <label class='active' for='rollno'>Roll No</label>
                </div>
                <div class='input-field'>
                <input type='text' id='name' value=$user->name class='validate'/>
                <label class='active' for='name'>Name</label>
                </div>
                <div class='input-field'>
                <input type='text' id='fname' value=$user->father class='validate'/>
                <label class='active' for='fname'>Father Name</label>
                </div>
                <div class='input-field'>
                <input type='text' id='branch' value=$user->branch class='validate'/>
                <label class='active' for='branch'>Branch</label>
                </div>
                <div class='input-field'>
                <input type='text' id='year' value=$user->year class='validate'/>
                <label class='active' for='year'>Year</label>
                </div>
                <div class='input-field'>
                <input type='text' id='email' value=$user->email class='validate'/>
                <label class='active' for='email'>E-Mail</label>
                </div>
                <div class='input-field'>
                <input type='text' id='address' value=$user->address class='validate'/>
                <label class='active' for='address'>Address</label>
                </div>
                </fieldset>
                <br><button onclick='save()' class='btn waves-effect waves-light' type='button' name='editbutton'>SAVE</button>
              </form>";
              exit();
            }
        }
      }

      if($_GET['flag'] == 2){
        $rollno = $_GET['rollno'];
        $password = $_GET['password'];
        $xml = simplexml_load_file('users.xml');
        $users = $xml->user;
        foreach($users as $user){
          if($user->rollno == $rollno){
            if($user->password == $password){
              $_SESSION['rollno'] = $rollno;
              echo 1;
              exit();
            } else {
              echo "Incorrect Password";
              exit();
            }
          }
        }
        echo "Invalid User";
        exit();
      }

      if($_POST['flag'] == 2){
        $rollno = $_SESSION['rollno'];
        $name = $_POST['name'];
        $branch = $_POST['branch'];
        $year = $_POST['year'];
        $email = $_POST['email'];
        $address = $_POST['address'];
        $fname = $_POST['fname'];

        $xml = simplexml_load_file('users.xml');
        $users = $xml->user;
        foreach($users as $user){
          if($user->rollno == $rollno){
            $user->name = $name;
            $user->branch = $branch;
            $user->year = $year;
            $user->email = $email;
            $user->father = $fname;
            $user->address = $address;
            $xml->asXML('users.xml');
            echo 1;
            exit();
          }
        }
      }

      if($_POST['flag'] == 3){
        $rollno = $_POST['rollno'];
        $name = $_POST['name'];
        $branch = $_POST['branch'];
        $year = $_POST['year'];
        $email = $_POST['email'];
        $address = $_POST['address'];
        $password = $_POST['password'];
        $fname = $_POST['fname'];

        $xml = simplexml_load_file('users.xml');
        $users = $xml->user;
        foreach($users as $user){
          if($user->rollno == $rollno){
            echo "RollNo already registered";
            exit();
          }
        }

        $child = $xml->addchild('user');
        $child->addchild("userid", (int)$user->userid + 1);
        $child->addchild("rollno", $rollno);
        $child->addchild("name", $name);
        $child->addchild("father", $fname);
        $child->addchild("branch", $branch);
        $child->addchild("year", $year);
        $child->addchild("email", $email);
        $child->addchild("password", $password);
        $child->addchild("address", $address);
        $xml->asXML('users.xml');
        echo 1;
        exit();
      }
?>
