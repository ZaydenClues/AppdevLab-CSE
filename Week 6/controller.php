<?php

session_start();
error_reporting(E_ALL & ~ E_NOTICE);
require 'vendor/autoload.php';

class Controller
{
    function __construct() {
        $this->processEmailVerification();
    }
    function processEmailVerification()
    {
        switch ($_POST["action"]) {
            case "get_otp":
                $rollno = $_POST['rollno'];
                $useremail = '';
                $name = '';
                $xml = simplexml_load_file('users.xml');
                $users = $xml->user;
                foreach($users as $user){
                    if($user->rollno == $rollno){
                      $useremail = $user->email;
                      $name = $user->name;
                    }
                  }
                $key = "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";
                $email = new \SendGrid\Mail\Mail();
                $otp = rand(100000, 999999);
                $_SESSION['session_otp'] = $otp;
                $email->setFrom("sarankaarthik@gmail.com", "no-reply");
                $email->setSubject("OTP for editing");
                $email->addTo(strval($useremail), strval($name));
                $email->addContent("text/plain", "Your OTP is ".$otp);
                $sendgrid = new \SendGrid($key);
                try {
                    $response = $sendgrid->send($email);
                    echo "<h5 align='center'>OTP has been sent to your mail</h5>
                      <form class='form'>
                      <div class='input-field'>
                      <input type='text' id='otp' class='validate'/>
                      <label class='active' for='otp'>OTP</label>
                      </div>
                      <br><button onclick='verifyOTP()' class='btn waves-effect waves-light' type='button' name='editbutton'>VERIFY</button>";
                } catch (Exception $e) {
                    echo 'Caught exception: '. $e->getMessage() ."\n";
                }
                break;

            case "verify_otp":
                $otp = $_POST['otp'];

                if ($otp == $_SESSION['session_otp'])
                {
                   unset($_SESSION['session_otp']);
                   echo 1;
                }
                else {
                    echo "Incorrect OTP";
                }
                break;
        }
    }
}
$controller = new Controller();
?>
