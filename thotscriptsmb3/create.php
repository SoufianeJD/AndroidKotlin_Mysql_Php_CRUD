<?php

require_once('connection.php');

$name = $_POST['name'];
$lastname = $_POST['lastname'];
$level = $_POST['level'];
$number = $_POST['number'];
$mail = $_POST['mail'];
$gender = $_POST['gender'];

if(!$name || !$lastname || !$level  || !$number  || !$mail || !$gender){
  echo json_encode(array('message'=>'required field is empty.'));
}else{

  $query = mysqli_query($CON, "INSERT INTO student VALUES ('$name','$lastname','$level' ,'$number','$mail','$gender')");

  if($query){
    echo json_encode(array('message'=>'student data successfully added.'));
  }else{
    echo json_encode(array('message'=>'student data failed to add.'));
  }

}

?>
