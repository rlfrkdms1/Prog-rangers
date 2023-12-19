import { css } from "@emotion/react";
import React, { useState } from 'react';
import { 
  Fragment,
  SelectBox2,
  Wrapper2,
  OptionBox
} from "./FilterBox";
import ToggleDown from '../../assets/icons/main-toggle-down.svg';
import ToggleUp from '../../assets/icons/main-toggle-up.svg';
import { useAtom } from 'jotai';
import { targetAtom, targetScope, nameAtom, nameScope, valueAtom, valueScope } from "../../pages/BoardPage/AddSolution";

export const FilterBar2 = ({title, options, onSelect, secondWidth}) => {
  const [ selectedOption, setSelectedOption ] = React.useState(options[0]); 
  const [ isOpen, setIsOpen ] = React.useState(false);
  const [ isClicked, setIsClicked ] = useState(false);
  const [ value, setValue ] = useAtom( valueAtom, valueScope );
  const [ target, setTarget ] = useAtom( targetAtom, targetScope );
  const [ name, setName ] = useAtom( nameAtom, nameScope );

  const filterBarId = `filterbar${title}`;

  const handleToggle = () => {
    setIsOpen(!isOpen);
  };


  const handleSelect = (selected) => {
    setSelectedOption(selected);
    setIsClicked(true);
    setTarget(selected.name);
    setValue(selected.value);
    setName(title);
    onSelect(selected); // 선택된 옵션에 대한 처리를 상위 컴포넌트로 전달
    setIsOpen(false);
  };

  const closeFilterBarOnOutsideClick = (e) => {
    if (isOpen) {
      const filterBar = document.getElementById(filterBarId);
      if (filterBar && !filterBar.contains(e.target)) {
        setIsOpen(false);
      }
    }
  };

  React.useEffect(() => {
    window.addEventListener('click', closeFilterBarOnOutsideClick);
    return () => {
      window.removeEventListener('click', closeFilterBarOnOutsideClick);
    };
  }, [isOpen]);

  return(
    <div id={filterBarId} css={css`${Fragment}`}>
      <div 
        css={css`
        ${SelectBox2};
        border-radius: ${isOpen ? '25px 25px 0 0' : '25px'};
        `}
        value={selectedOption ? selectedOption.value : 'ALL'}
        onChange={handleSelect}
        onClick={handleToggle}
      >
        <div
          css={css`
            font-size: 18px;
            color: ${isClicked ? '#757575' : '#959595'};
            margin-left:30px;
            padding-top: 7px;
            width: ${secondWidth || '120px'};
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
      {isOpen && ( 
      <div css={css`${Wrapper2({isOpen})}`}>
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
      )}   
    </div>
  );
};