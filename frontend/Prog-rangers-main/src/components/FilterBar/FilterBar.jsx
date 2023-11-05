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
import { useAtom } from 'jotai';
import { targetAtom, targetScope, nameAtom, nameScope, valueAtom, valueScope } from "../../pages/BoardPage/AddSolution";
import { useEffect } from "react";

export const FilterBar = ({title, options, width, secondWidth}) => {
  const [ selectedOption, setSelectedOption ] = useState(options[0]); 
  const [ isOpen, setIsOpen ] = useState(false);
  const [ isClicked, setIsClicked ] = useState(false);
  const [ value, setValue ] = useAtom( valueAtom, valueScope );
  const [ target, setTarget ] = useAtom( targetAtom, targetScope );
  const [ name, setName ] = useAtom( nameAtom, nameScope );

  const handleToggle = () => {
    setIsOpen(!isOpen);
  };
  const handleSelect = (e) => {
    setSelectedOption(e);
    setIsClicked(true);
    setTarget(e.name);
    setValue(e.value);
    setName(title);
  };

  return(
    <div css={css`${Fragment}`}>
      <div 
        css={css`
        ${SelectBox};
        border-radius: ${isOpen ? '25px 25px 0 0' : '25px'};
        width: ${width || '230px'};
        `}
        value={selectedOption ? selectedOption.value : 'ALL'}
        onChange={handleSelect}
        onClick={handleToggle}
      >
        <div
          css={css`
            font-size: 20px;
            color: #959595;
            margin-left:30px;
            padding-top: 9px;
            width: ${secondWidth || '165px'};
            font-weight: ${isClicked ? 'bold' : 'normal'};
          `}
        >
          {selectedOption.name}
        </div>
        {isOpen?(
          <img css={css`width: 18px; height: 100%; &:hover{cursor: pointer;} `} src={ToggleUp} alt="toggle_up" onClick={handleToggle}/>
        ) : (
          <img css={css`width: 18px; height: 100%; &:hover{cursor: pointer;}`} src={ToggleDown} alt="toggle_down" onClick={handleToggle}/>
        )
        }
      </div>  
      <div css={css`${Wrapper({isOpen})} width: ${width || '230px'};`}>
          {options.map((item, index) => (
              <div
                css={css`
                  ${OptionBox}
                `}
                key={index}
                data-value={item.value}
                onClick={()=>(handleSelect(item))}
              >
                {item.name}
              </div> 
            ))}
      </div>  
    </div>
  );
};