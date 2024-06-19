import {
  HeadFieldset,
  Descript,
  HeadForm,
  Search,
  SubmitBtn,
  TitleLegend,
} from "./styles";

const Header = () => {
  return (
    <HeadForm>
      <HeadFieldset>
        <TitleLegend>Welcome to Our 2nd Novel</TitleLegend>
        <Descript>Discover behind epiosde</Descript>
        <Search type="text" placeholder="Search novel or author"></Search>
        <SubmitBtn type="submit"> Explore </SubmitBtn>
      </HeadFieldset>
    </HeadForm>
  );
};
export default Header;
