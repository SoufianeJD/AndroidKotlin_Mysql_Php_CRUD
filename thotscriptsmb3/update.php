<?php

require_once('connection.php');

$name = $_POST['name'];
$lastname = $_POST['lastname'];
$mail = $_POST['mail'];
$level = $_POST['level'];
$number = $_POST['number'];
$gender = $_POST['gender'];

if(!$name || !$lastname || !$number  || !$level  || !$mail || !$gender){
  echo json_encode(array('message'=>'required field is empty.'));
}else{

  $query = mysqli_query($CON, "UPDATE student SET lastname='$lastname', mail='$mail', level='$level' , number='$number', gender='$gender' WHERE name = '$name'");

  if($query){
    echo json_encode(array('message'=>'student data successfully updated.'));
  }else{
    echo json_encode(array('message'=>'student data failed to update.'));
  }

}


?>
