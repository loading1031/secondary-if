import { useForm } from "react-hook-form";
import { useNavigate, useOutletContext } from "react-router-dom";
import axios from "axios";
import {
  FormContainer,
  StyledButton,
  StyledFieldset,
  StyledInput,
  StyledLegend,
  WarningP,
} from "./styles";

function Login() {
  const {
    register,
    handleSubmit,
    watch,
    formState: { isSubmitting, isSubmitted, errors },
  } = useForm({ mode: "onChange" });

  // watch를 사용하여 모든 필드의 값을 가져옴
  const formData = watch();

  // 각 필드가 null이거나 비어있는지 확인
  const btnDisable = () => {
    return (
      isSubmitting ||
      !formData.name ||
      Object.keys(errors).length > 0
    );
  };

  const navigate = useNavigate();
  const { handleAuthentication } = useOutletContext();

  const onSubmit = async (data) => {
    try {
      // Assume login endpoint returns a token on successful login
      const loginResponse = await axios.post(
        "/api/members",
        data
      );
      if (loginResponse.status === 200){
        alert(`${data.name}님 환영합니다.`)
        const token = loginResponse.data.accessToken; // Getting token from response
        handleAuthentication(token);
        navigate('/');
      }

    } catch (error) {
      if (error.response) {
        // Handle responses with specific status codes
        if (error.response.status === 404) {
          alert("User not found");
        } else {
          alert(error.response.data.message);
        }
      } else {
        alert("An error occurred during login");
      }
    }
  };

  return (
    <FormContainer onSubmit={handleSubmit(onSubmit)}>
      <StyledFieldset>
        <StyledLegend>로그인 페이지</StyledLegend>
        <StyledInput
          id="name"
          type="text"
          placeholder="아이디"
          aria-invalid={
            isSubmitted ? (errors.id ? "true" : "false") : undefined
          }
          {...register("name", {
            required: "이 필수 입력입니다.",
          })}
        />
        {errors.name && (
          <WarningP role="alert">{errors.name.message}</WarningP>
        )}
        {errors.password && (
          <WarningP role="alert">{errors.password.message}</WarningP>
        )}
        <StyledButton type="submit" disabled={btnDisable()}>
          로그인
        </StyledButton>
      </StyledFieldset>
    </FormContainer>
  );
}

export default Login;