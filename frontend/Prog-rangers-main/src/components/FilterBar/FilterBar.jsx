import { css } from "@emotion/react";
import React, { useState } from 'react';
import { 
  Fragment,
  SelectBox,
  OptionBox,
  ToggleIcon
} from "./FilterBox";
import ToggleDown from '../../assets/icons/main-toggle-down.svg';
import ToggleUp from '../../assets/icons/main-toggle-up.svg';
import { useEffect } from "react";

export const FilterBar = ({options}) => {
  const [ selectedOption, setSelectedOption ] = useState('ALL'); 
  const [ isOpen, setIsOpen ] = useState(false);

  const handleToggle = () => {
    setIsOpen(!isOpen);
  };
  const handleSelect = (e) => {
    setSelectedOption(e.target.value);
  };

  console.log(selectedOption);

  return(
    <div css={css`${Fragment}`}>
      <div 
        css={css`
        ${SelectBox}
      `}
        value={selectedOption}
        onChange={handleSelect}
      >
        <div
          css={css`
            font-size: 20px;
            color: #959595;
            margin-left:30px;
            padding-top: 9px;
          `}
        >
         알고리즘
        </div>
        {isOpen?(
          <img css={css`width: 18px; height: 100%; margin: 0 20px 21px 77px;`} src={ToggleUp} alt="toggle_up" onClick={handleToggle}/>
        ) : (
          <img css={css`width: 18px; height: 100%; margin: 0 20px 21px 77px;`} src={ToggleDown} alt="toggle_down" onClick={handleToggle}/>
        )
        }
      </div>  
      <div css={css`display: ${isOpen ? "flex" : "none"}; flex-direction: column; width:  230px; `}>
        {options.map((item, index) => (
          <div
            css={css`
              ${OptionBox}
            `}
            key={index}
            value={item.value}
            onClick={handleSelect}
          >
            {item.name}
          </div>
        ))}
      </div>  
    </div>
  );
};