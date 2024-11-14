import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {
  FormContainer,
  StyledButton,
  StyledFieldset,
  StyledInput,
  StyledContent,
  StyledLegend,
  WarningP,
} from "./styles";

function CreateUpload() {
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

  const onSubmit = async (data) => {
    try {
      const token = localStorage.getItem('token'); // 예시: 로컬 스토리지에서 토큰 가져오기
      // Assume login endpoint returns a token on successful login
      const loginResponse = await axios.post(
        `/api/artifacts/{data.artifactId}/upload`,
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (loginResponse.status === 200){
        alert(`${data.content}을 성공적으로 업로드했습니다.`)
        navigate('/');
      }

    } catch (error) {
      if (error.response) {
        // Handle responses with specific status codes
        alert(error.response.data.message);
      } else {
        alert("An error occurred during login");
      }
    }
  };

  return (
    <FormContainer onSubmit={handleSubmit(onSubmit)}>
      <StyledFieldset>
        <StyledLegend>업로드 작성</StyledLegend>
        
        <StyledInput
          id="artifactId"
          type="Int"
          placeholder="작품Id"
          aria-invalid={
            isSubmitted ? (errors.id ? "true" : "false") : undefined
          }
          {...register("artifactId", {
            required: "이 필수 입력입니다.",
          })}
        />
        {errors.content && (
          <WarningP role="alert">{errors.name.message}</WarningP>
        )}
        <StyledContent
          id="content"
          type="text"
          placeholder="내용"
          aria-invalid={
            isSubmitted ? (errors.id ? "true" : "false") : undefined
          }
          {...register("content", {
            required: "이 필수 입력입니다.",
          })}
        />
        {errors.content && (
          <WarningP role="alert">{errors.name.message}</WarningP>
        )}
        <StyledInput
          id="prevId"
          type="Int"
          placeholder="이전 회차 Id"
          aria-invalid={
            isSubmitted ? (errors.id ? "true" : "false") : undefined
          }
          {...register("prevId", {
            required: "이 필수 입력입니다.",
          })}
        />
        {errors.prevId && (
          <WarningP role="alert">{errors.name.message}</WarningP>
        )}
        <StyledButton type="submit" disabled={btnDisable()}>
          업로드
        </StyledButton>
      </StyledFieldset>
    </FormContainer>
  );
}
export default CreateUpload;
