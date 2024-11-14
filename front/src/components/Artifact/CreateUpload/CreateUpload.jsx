import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

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
  const artifact = useSelector((state) => state.search.finalSearchRes);

  const {
    register,
    handleSubmit,
    watch,
    reset,
    formState: { isSubmitting, isSubmitted, errors },
  } = useForm({ 
    mode: "onChange",
   });

   // artifactId 업데이트 시 reset을 사용하여 동기화
  useEffect(() => {
    if (artifact?.artifactId) {
      reset({
        artifactId: artifact.artifactId,
        content: "",
        prevId: "",
      });
    }
  }, [artifact, reset]);

  // watch를 사용하여 모든 필드의 값을 가져옴
  const formData = watch();

  // 각 필드가 null이거나 비어있는지 확인
  const btnDisable = () => {
    return (
      isSubmitting ||
      !formData.artifactId || !formData.content || !formData.prevId ||
      Object.keys(errors).length > 0
    );
  };

  const navigate = useNavigate();

  const onSubmit = async (data) => {
    console.log(`request_body: ${data}`);
    try {
      const token = localStorage.getItem('token'); // 예시: 로컬 스토리지에서 토큰 가져오기
      // Assume login endpoint returns a token on successful login
      const loginResponse = await axios.post(
        `/api/artifacts/${data.artifactId}/upload`,
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (loginResponse.status === 200){
        alert(`${data.content}을 성공적으로 업로드했습니다.`)
        reset();
        navigate('/');
      }

    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          // 401 Unauthorized 에러 처리: 로그인 페이지로 이동
          alert("로그인을 먼저 해주세요.");
          navigate('/login');
        } else {
          console.log(`message: ${error.response.data.message}`);
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
        { artifact ?
          <StyledLegend>{artifact.title} 업로드 작성</StyledLegend> 
          : <StyledLegend>업로드할 작품을 검색해주세요</StyledLegend> 
        }
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
          <WarningP role="alert">{errors.content.message}</WarningP>
        )}
        <StyledInput
          id="prevId"
          type="number"
          placeholder="이전 회차 Id"
          aria-invalid={
            isSubmitted ? (errors.id ? "true" : "false") : undefined
          }
          {...register("prevId", {
            required: "이 필수 입력입니다.",
          })}
        />
        {errors.prevId && (
          <WarningP role="alert">{errors.prevId.message}</WarningP>
        )}
        <StyledButton type="submit" disabled={btnDisable()}>
          업로드
        </StyledButton>
      </StyledFieldset>
    </FormContainer>
  );
}
export default CreateUpload;
