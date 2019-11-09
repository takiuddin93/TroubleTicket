<?php
require "connection.php";
$name = mysqli_real_escape_string($connection, $_POST['name']);
$phone = mysqli_real_escape_string($connection, $_POST['phone']);
$email = mysqli_real_escape_string($connection, $_POST['email']);
$password = mysqli_real_escape_string($connection, $_POST['password']);

$sql = "SELECT * FROM customers WHERE phone = '$phone'";
$query = mysqli_query($connection, $sql);

if (mysqli_num_rows($query) > 0) {
  $status = "exists";
} else {
  $sql = "INSERT INTO customers(name, phone, email, password) VALUES ('$name', '$phone', '$email', '$password')";
  $query = mysqli_query($connection, $sql);
  if ($query) {
    $status = "ok";
  } else {
    $status = "error";
  }
}
echo json_encode(array('response' => $status));

mysqli_close($connection);
?>
