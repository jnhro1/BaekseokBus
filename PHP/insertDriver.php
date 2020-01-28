<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $userId=$_POST['userId'];
        $userPw=$_POST['userPw'];
		$userName=$_POST['userName'];
        $userBirth=$_POST['userBirth'];
		$userPhone=$_POST['userPhone'];
		$driverBus=$_POST['driverBus'];
		$userRank = "driver";



        if(empty($userId)){
            $errMSG = "ID를 입력하세요.";
        }
        else if(empty($userPw)){
            $errMSG = "PW를 입력하세요.";
        }

        if(!isset($errMSG)) // 이름과 나라 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO USER(userId, userPw, userName, userBirth, userPhone,driverBus,userRank) 
				VALUES(:userId, :userPw, :userName, :userBirth, :userPhone, :driverBus, :userRank)');
                $stmt->bindParam(':userId', $userId);
                $stmt->bindParam(':userPw', $userPw);
				$stmt->bindParam(':userName', $userName);
                $stmt->bindParam(':userBirth', $userBirth);
				$stmt->bindParam(':userPhone', $userPhone);
				$stmt->bindParam(':driverBus', $driverBus);
				$stmt->bindParam(':userRank', $userRank);
				

                if($stmt->execute())
                {
                    $successMSG = "회원가입에 성공하였습니다.";
                }
                else
                {
                    $errMSG = "회원가입에 실패하였습니다.";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                userId: <input type = "text" name = "userId" />
                userPw: <input type = "text" name = "userPw" />
				userName: <input type = "text" name = "userName" />
                userBirth: <input type = "text" name = "userBirth" />
				userPhone: <input type = "text" name = "userPhone" />
				driverBus: <input type = "text" name = "driverBus" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>