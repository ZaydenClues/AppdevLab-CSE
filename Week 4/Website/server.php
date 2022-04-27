<?php
$host = "localhost";
$user = "saran";
$password = "123456";
$dbname = "eventreg";
$con = pg_connect("host=$host dbname=$dbname user=$user password=$password");

if (!$con) {
die('Connection failed.');
}

if($_GET['flag']){
   $event = $_GET['event'];
   $query = "select fees from events where event = '$event'";
   $result = pg_query($con, $query);
   $fees = 0;
   while ($row = pg_fetch_row($result)) {
     $fees = $row[0];
   }
   echo json_encode($fees);
 }

 if($_POST['flag']){
   $name = $_POST['name'];
   $rollno = $_POST['rollno'];
   $course = $_POST['course'];
   $events = $_POST['events'];
   $check = pg_query($con,"select event from regis where rollno='$rollno'");
   if(pg_num_rows($check) > 0){
     $del = "delete from regis where rollno='$rollno'";
     pg_query($del);
   }
   foreach($events as $event){
     $query = "insert into regis values('$name','$rollno','$course','$event')";
     pg_query($con,$query);
   }
   echo "Events are registered!";
   exit();
 }

if($_GET['auto']){
  $rollno = $_GET['rollno'];
  $query = "select name,rollno,course,string_agg(regis.event, ',') as events,sum(fees) from regis inner join events on regis.event = events.event where rollno='$rollno' group by name,rollno,course;
";
  $result = pg_query($con,$query);
  $name = "";
  $course = "";
  $events = array();
  $fees = 0;
  while ($row = pg_fetch_row($result)) {
    $name = $row[0];
    $course = $row[2];
    array_push($events,$row[3]);
    $fees = $row[4];
  }
  $response = array();
  array_push($response, array($name,$rollno,$course,$fees));
  array_push($response, $events);
  echo json_encode($response);
  exit();
}


 if($_GET['list']){
   $query = "select name,rollno,course,string_agg(regis.event, ',') as events,sum(fees) from regis inner join events on regis.event = events.event group by name,rollno,course;";
   $result = pg_query($con,$query);
   while ($row = pg_fetch_row($result)) {
     echo "<tr value='$row[1]'><td>".$row[0]."</td>";
     echo "<td>".$row[1]."</td>";
     echo "<td>".$row[2]."</td>";
     echo "<td>".$row[3]."</td>";
     echo "<td>".$row[4]."</td>";
     echo "<td><button class='btn waves-effect waves-light' type='button' onclick='edit(this.value)' value='$row[1]' name='button'>Edit</button><button class='btn waves-effect waves-light' type='button' value='$row[1]' onclick='del(this.value)' name='button'>Delete</button></td></tr>";

   }
 }

 if($_GET['del']){
   $rollno = $_GET['rollno'];
   $del = "delete from regis where rollno='$rollno'";
   pg_query($del);
 }


// $flag = $_GET['flag'];
//
// if($flag){
//   $name = $_GET['name'];
//   $rollno = $_GET['rollno'];
//   $course = $_GET['course'];
//   $eventa = $_GET['eventa'];
//   $eventb = $_GET['eventb'];
//   $eventc = $_GET['eventc'];
//   $eventd = $_GET['eventd'];
//   $fees = $_GET['fees'];
//
//   $query1 = "insert into regis values(1,'$name','$rollno','$course','$eventa', '$fees')";
//   $query2 = "insert into regis values('$name',$rollno,'$course','$eventb', $fees)";
//   $query3 = "insert into regis values('$name',$rollno,'$course','$eventc', $fees)";
//   $query4 = "insert into regis values('$name',$rollno,'$course','$eventd', $fees)";
//
//   $result = pg_query($con, $query1);
//   $result = pg_query($con, $query2);
//   $result = pg_query($con, $query3);
//   $result = pg_query($con, $query4);
//
//  } else {
//    $event = $_GET['event'];
//    $query = "select fees from events where event = '$event'";
//    $result = pg_query($con, $query);
//    $fees = 0;
//    while ($row = pg_fetch_row($result)) {
//      $fees = $row[0];
//    }
//    echo json_encode($fees);
//  }



?>
