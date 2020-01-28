<?php

$con = mysqli_connect("localhost","buswlab","bckim1018!","buswlab");
mysqli_query($con, 'SET NAMES utf8');

$userId = $_POST['userId2'];
echo $userId;
$statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userId = ?");

mysqli_stmt_bind_param($statement, "s", $userId);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $userId, $userPw,$userRank, $userName, $userPhone, $userBirth, $userPoint, $driverBus);

$response = array();
$response["success"] = true;
$response["userName"] = $userName;

while(mysqli_stmt_fetch($statement)) {
	$response["success"] = true;
	$response["userId"] = $userId;
	$response["userPw"] = $userPw;
	$response["userRank"] = $userRank;
	$response["userName"] = $userName;
	$response["userPhone"] = $userPhone;
	$response["userBirth"] = $userBirth;
	$response["userPoint"] = $userPoint;
}

echo json_encode($response);


?>