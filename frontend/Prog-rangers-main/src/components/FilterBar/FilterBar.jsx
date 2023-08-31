import { css } from "@emotion/react";
import React, { useState } from 'react';
import { 
  Fragment,
  SelectBox,
  Wrapper,
  OptionBox,
  ToggleIcon
} from "./FilterBox";
import ToggleDown from '../../assets/icons/main-toggle-down.svg';
import ToggleUp from '../../assets/icons/main-toggle-up.svg';

export const FilterBar = ({options}) => {
  const [ selectedOption, setSelectedOption ] = useState({
    value: 'ALL',
    name: '알고리즘',
  }); 
  const [ isOpen, setIsOpen ] = useState(false);
  const [ isClicked, setIsClicked ] = useState(false);

  const handleToggle = () => {
    setIsOpen(!isOpen);
  };
  const handleSelect = (e) => {
    setSelectedOption(e);
    setIsClicked(true);
  };

  console.log(selectedOption);

  return(
    <div css={css`${Fragment}`}>
      <div 
        css={css`
        ${SelectBox};
        border-radius: ${isOpen ? '25px 25px 0 0' : '25px'};
        `}
        value={selectedOption ? selectedOption.value : 'ALL'}
        onChange={handleSelect}
      >
        <div
          css={css`
            font-size: 20px;
            color: #959595;
            margin-left:30px;
            padding-top: 9px;
            width: 165px;
            text-align: ${isClicked ? 'center' : 'left'};
            font-weight: ${isClicked ? 'bold' : 'normal'};
          `}
        >
         {selectedOption ? selectedOption.name : '알고리즘'}
        </div>
        {isOpen?(
          <img css={css`width: 18px; height: 100%; &:hover{cursor: pointer;}`} src={ToggleUp} alt="toggle_up" onClick={handleToggle}/>
        ) : (
          <img css={css`width: 18px; height: 100%; &:hover{cursor: pointer;}`} src={ToggleDown} alt="toggle_down" onClick={handleToggle}/>
        )
        }
      </div>  
      <div css={css`${Wrapper({isOpen})}`}>
        {options.map((item, index) => (
            <div
              css={css`
                ${OptionBox}
              `}
              key={index}
              data-value={item.value}
              onClick={()=>handleSelect(item)}
            >
              {item.name}
            </div> 
          ))}
      </div>  
    </div>
  );
};