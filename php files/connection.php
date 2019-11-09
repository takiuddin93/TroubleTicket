<?php
$host = "localhost";
$userName = "plusequa_comp"
$userPassword = "test_complain"
$dbName = "plusequa_complaints"

$connection = mysqli_connect($host, $userName, $userPassword, $dbName);
if (!$connect) {
  echo "Error: " . mysqli_connect_error();
  exit();
}
?>
