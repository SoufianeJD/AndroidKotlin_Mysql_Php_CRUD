<?php

require_once('connection.php');

$name = $_GET['name'];

if(!$name){
  echo json_encode(array('message'=>'required field is empty'));
}else{

  $query = mysqli_query($CON, "DELETE FROM student WHERE name='$name'");

  if($query){
    echo json_encode(array('message'=>'student data successfully deleted.'));
  }else{
    echo json_encode(array('message'=>'student data failed to delete.'));
  }

}


?>
