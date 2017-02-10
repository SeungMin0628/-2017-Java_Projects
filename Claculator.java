import java.util.Scanner;

/* 계산기
 * 목적 : 키보드에서 수식을 입력받아 계산하기
 * Level 1. 산술연산자의 우선 순위가 동일한 수식의 계산
 * 		- 수식의 유효성 검사
 * 		- 음수를 고려, 단 음수는 항상 괄호 표
 * Level 2. 괄호 없는 수식의 계산, 산술연산자의 우선순위를 고려할 것
 * Level 3. 괄호 있는 수식의 계산, 산술연산자의 우선순위를 고려할 것
*/

class IdioticCirculater {
	// 멤버 변수
	Scanner scan = new Scanner(System.in);
	
	String	formula; // 계산식
	int		Count; // 계
	
	// 생성자
	IdioticCirculater() {
		formula = "";
		Count = 0;
	}
	
	// 멤버 메서드
	String scanString() {
		String	userStr;
		String	refiStr;
		int		size;
		
		do {
			System.out.println("Type a Formula : ");
			scan = new Scanner(System.in);
			
			refiStr = "";
			userStr = scan.nextLine();
			size = userStr.length();
			
			for (int iCount = 0; iCount < size; iCount++) {
				if (userStr.charAt(iCount) != 32) 
					refiStr += userStr.charAt(iCount);
			}
			size = refiStr.length();
		} while (!(checkString(refiStr, size)));
		
		if (refiStr.charAt(size-1) != '='){
			refiStr += "=";
			size++;
		}
		
		return refiStr;
	} // 문자열 입력받는 함수
	
	boolean checkString(String inputStr, int size) {
		final int	NUMBER = 1;
		final int	SIGN = 2;
		final int	BRACKET_OPEN = 3;
		final int	BRACKET_CLOSE = 4;// 검사하는 문자의 이전 문자가 무엇인지 결정
		
		int		before = 0;
		int		bracket_flag = 0;
		char	nowChar;
		
		for (int iCount = 0; iCount < size; iCount++) {
			nowChar = inputStr.charAt(iCount);
			
			switch(before) {
			case NUMBER :
				switch(nowChar) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					before = NUMBER;
					break;
				case '+':
				case '-':
				case '*':
				case '/':
					before = SIGN;
					break;
				case ')':
					if (bracket_flag > 0) {
						before = BRACKET_CLOSE;
						bracket_flag -= 1;
						break;
					} else {
						System.out.println("ERROR : 식을 잘못 입력했습니다.");
						return false;
					}
				case '=':
					if (iCount == size - 1)
						break;
					else {
						System.out.println("ERROR : 식을 잘못 입력했습니다.");
						return false;
					}
				default:
					System.out.println("ERROR : 식을 잘못 입력했습니다.");
					return false;
				}				
				break;
			case SIGN :
				switch(nowChar) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					before = NUMBER;
					break;
				case '(':
					before = BRACKET_OPEN;
					bracket_flag += 1;
					break;
				default:
					System.out.println("ERROR : 식을 잘못 입력했습니다.");
					return false;
				}
				break;
			case BRACKET_OPEN :
				switch(nowChar) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					before = NUMBER;
					break;
				case '(':
					before = BRACKET_OPEN;
					bracket_flag += 1;
					break;
				case '-':
				case '+':
					before = SIGN;
					break;
				default:
					System.out.println("ERROR : 식을 잘못 입력했습니다.");
					return false;
				}
				break;
			case BRACKET_CLOSE:
				switch(nowChar) {
				case '+':
				case '-':
				case '*':
				case '/':
					before = SIGN;
					break;
				case '=':
					if (iCount == size - 1) 
						break;
					else {
						System.out.println("ERROR : 식을 잘못 입력했습니다.");
						return false;
					}
				default:
					System.out.println("ERROR : 식을 잘못 입력했습니다.");
					return false;
				}
				break;
			default:
				switch(nowChar) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					before = NUMBER;
					break;
				case '(':
					before = BRACKET_OPEN;
					bracket_flag += 1;
					break;
				default:
					System.out.println("ERROR : 식을 잘못 입력했습니다.");
					return false;
				}
				break;
			}
			
		}
		
		if (bracket_flag > 0)
			return false;
		
		return true;
	} // 계산식이 올바른지 검사하는 함수
	
	double circulate() {
		// 변수
		double	num1, num2, num3, result; // 숫자 1, 2, 3, 결과값
		int		size, numTemp; // 문자열 크기, 숫자 임시 저장
		char	sign1, sign2, nowChar; // 부호 1, 부호 2, 현재 문자
		boolean	negativeFlag, num1Flag, num2Flag, multiFlag; // 음수 판독, 숫자1 판독, 숫자2 판독, 곱셈 판독
		
		// 초기화
		num1 = num2 = num3 = result = 0;
		negativeFlag = num1Flag = num2Flag = multiFlag = false;
		sign1 = sign2 = nowChar = 0;
		size = formula.length();
		
		// 계산
		for (; Count < size; Count++) {
			nowChar = formula.charAt(Count);
			
			switch (nowChar) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				numTemp = nowChar - '0';
				if (num1Flag != true)
					num1 = num1 * 10 + numTemp;
				else if (num2Flag != true)
					num2 = num2 * 10 + numTemp;
				else
					num3 = num3 * 10 + numTemp;
				break;
			case '(':
				if (num2Flag == true) {
					Count++;
					num3 = circulate();
					break; // 숫자2까지 채워져있을 경우
				} else if(num1Flag == true) {
					Count++;
					num2 = circulate();
					break; // 숫자1까지 채워져있을 경우
				} else {
					Count++;
					num1 = circulate();
					break; // 숫자가 채워져있지 않을 때
				}
			case ')':
				if (num2Flag == true) {
					if (sign2 == '+' || sign2 == '-') {
						num1 = multiple(num1, num2, sign1);
						return plus(num1, num3, sign2);						
					} else {
						num2 = multiple(num2, num3, sign2);
						return plus(num1, num2, sign1);
					} // 숫자1과 숫자2, 숫자3이 채워져 있을 때
				} else if (num1Flag == true) {
					if (sign1 == '+' || sign1 == '-') {
						return plus(num1, num2, sign1);
					} else {
						return multiple(num1, num2, sign1);
					} // 숫자1과 숫자2가 채워져 있을 때
				} else {
					if (negativeFlag == true) {
						num1 *= (-1);
						negativeFlag = false;
					}
					return num1;// 숫자1만 채워져 있을 때
				}
			case '-':
				if (formula.charAt(Count-1) == '(') {
					negativeFlag = true;
					break;
				}
			case '+':
				if (num1Flag != true) {
					num1Flag = true;
					sign1 = nowChar;
					break; // 첫번째 기호로 마주친 경우. 숫자1을 닫고 기호1에 자신을 채운다.
				} else if (num2Flag != true) {
					if (multiFlag == true) {
						num2Flag = true;
						sign2 = nowChar;
						break; // 첫번째 기호가 곱센연산인 경우. 숫자2를 닫고 기호2에 자신을 채운다.
					} else {
						num1 = plus(num1, num2, sign1);
						num2 = 0;
						sign1 = nowChar;
						break; // 첫번째 기호가 덧셈연산인 경우. 숫자1+숫자2를 숫자1에 담고 숫자2를 초기화, 기호1에 자신을 담는다.
					} // 두번째 기호로 마주친 경우.
				} else {
					if (sign1 == '*' || sign1 == '/') {
						num1 = multiple(num1, num2, sign1);
						num1 = plus(num1, num3, sign2);
						num2Flag = false;
						num2 = num3 = 0;
						sign1 = nowChar;
						sign2 = 0;
						multiFlag = false;
						break; // 첫번째 기호가 곱셈인 경우. (숫자1*숫자2)+숫자3을 숫자1에 담고, 숫자2를 열고 기호1에 자신을 담는다.
					} else {
						num2 = multiple(num2, num3, sign2);
						num1 = plus(num1, num2, sign1);
						num2Flag = false;
						num2 = num3 = 0;
						sign1 = nowChar;
						sign2 = 0;
						multiFlag = false;
						break; // 두번째 기호가 곱셈인 경우. 숫자1+(숫자2*숫자3)을 숫자1에 담고, 숫자2를 열고 기호1에 자신을 담는다.
					} // 세번째 기호로 마주친 경우.
				}
			case '/':
			case '*':
				if (num1Flag != true) {
					num1Flag = true;
					sign1 = nowChar;
					multiFlag = true;
					break; // 첫번째 기호로 마주친 경우. 숫자1을 닫고 기호1에 자신을 채운다.
				} else if (num2Flag != true) {
					if (multiFlag != true) {
						num2Flag = true;
						sign2 = nowChar;
						multiFlag = true;
						break; // 첫번째 기호가 곱셈연산이 아닐 경우. 숫자2를 닫고 기호2에 자신을 채운다.
					} else {
						num1 = multiple(num1, num2, sign1);
						num2 = 0;
						sign1 = nowChar;
						break; // 첫번째 기호가 곱셈일 경우. 숫자1*숫자2를 숫자1에 담고 숫자2를 초기화, 기호1에 자신을 채운다.
					} // 두번째 기호로 마주친 경우.
				} else {
					if (sign1 == '+' || sign1 == '-') {
						num2 = multiple(num2, num3, sign2);
						num3 = 0;
						sign2 = nowChar;
						break; // 첫번째 기호가 덧셈인 경우. 숫자2*숫자3을 숫자2에 담고, 기호2에 자신을 담는다.
					} else {
						num1 = multiple(num1, num2, sign1);
						num2 = num3;
						num3 = 0;
						sign1 = sign2;
						sign2 = nowChar;
						break; // 기호2가 덧셈인 경우. 숫자1*숫자2를 숫자1에 담고, 숫자3을 숫자2에 담는다. 기호2를 기호1로 옮기고, 기호2에 자신을 담는다.
					}// 세번째 기호로 마주친 경우. 
				}
			case '=':
				if (num2Flag == true) {
					if (sign2 == '+' || sign2 == '-') {
						num1 = multiple(num1, num2, sign1);
						result = plus(num1, num3, sign2);						
					} else {
						num2 = multiple(num2, num3, sign2);
						result = plus(num1, num2, sign1);
					}
					break; // 숫자1과 숫자2, 숫자3이 채워져 있을 때
				} else if (num1Flag == true) {
					if (sign1 == '+' || sign1 == '-') {
						result = plus(num1, num2, sign1);
					} else {
						result = multiple(num1, num2, sign1);
					} 
					break; // 숫자1과 숫자2가 채워져 있을 때
				} else {
					result = num1;
					break; // 숫자1만 채워져 있을 때
				} 
			}
		}
		
		return result;
	} // 계산 부분
	
	double multiple(double num1, double num2, char sign) {
		if (sign == '/') 
			num2 = 1/num2;
		
		return num1 * num2;
	} // 곱셈 함수
	
	double plus(double num1, double num2, char sign) {
		if (sign == '-')
			num2 *= (-1);
		
		return num1 + num2;
	} // 덧셈 함수
	
	void body() {
		formula = scanString();
		
		System.out.println("Circulate Result : " + circulate());
	} // 계산기 본체
}


public class Computer2 {
	public static void main(String[] args) {
		IdioticCirculater ic = new IdioticCirculater();		
		
		ic.body();
		
		System.out.println("Program End");
		
		return;
	}
}
