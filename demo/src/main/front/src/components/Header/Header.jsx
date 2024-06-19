import { useState, useEffect } from "react";
import axios from "axios";
import {
  HeadFieldset,
  Descript,
  HeadForm,
  Search,
  SubmitBtn,
  TitleLegend,
  SuggestionBox,
  SuggestionItem,
} from "./styles";

function Header() {
  const [input, setInput] = useState("");
  const [suggestions, setSuggestions] = useState([]);

  useEffect(() => {
    const timeoutId = setTimeout(async () => {
      if (!input.trim()) {
        setSuggestions([]);
        return; // 입력 값이 없는 경우 요청을 보내지 않습니다.
      }

      const endpoint = `http://localhost:8080/artifacts/search?title=${input}`;

      try {
        const response = await axios.get(endpoint);
        console.log(response.data);
        setSuggestions(response.data.result.postResDtos);
      } catch (error) {
        console.error("요청을 처리하는 중에 오류가 발생했습니다:", error);
      }
    }, 500); // 500ms 후에 실행
  }, [input]);
  return (
    <HeadForm>
      <HeadFieldset>
        <TitleLegend>Welcome to Our 2nd Novel</TitleLegend>
        <Descript>Discover behind epiosde</Descript>
        <Search
          type="text"
          placeholder="Search novel or author"
          onChange={(e) => setInput(e.target.value)}
        ></Search>
        {suggestions.length > 0 && (
          <SuggestionBox>
            {suggestions.map((suggestion) => (
              <SuggestionItem key={suggestion.artifactId}>
                {suggestion.title} by {suggestion.author}
              </SuggestionItem>
            ))}
          </SuggestionBox>
        )}
        <SubmitBtn type="submit"> Explore </SubmitBtn>
      </HeadFieldset>
    </HeadForm>
  );
}

export default Header;
