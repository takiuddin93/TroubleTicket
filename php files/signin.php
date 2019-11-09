<?php
require "connection.php";
$phone = $_GET['phone'];
$password = $_GET['password'];

$sql = "SELECT * FROM customers WHERE phone = '$phone' AND password = '$password' ";
$query = mysqli_query($connection, $sql);

if (!mysqli_num_rows($query) > 0) {
  $status = "failed";
  echo json_encode(array('response' => $status));
} else {
  $row = mysqli_fetch_assoc($query);
  $status = "ok";
  $name = $row['name'];
  $phone = $row['phone'];
  echo json_encode(array('response' => $status, 'name' => $name, 'phone' => $phone));
}

mysqli_close($connection);
?>
