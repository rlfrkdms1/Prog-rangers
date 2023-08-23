import React from 'react';
import {
  HeadingStyle,
  textStyle,
  codeBlockStyle,
  galleryStyle,
} from '../../styles/MainPage';

export default function Gallery({ title, Items, handleClick, id }) {
  function handleClickItem(id) {
    handleClick && handleClick(id);
  }

  return (
    <section>
      <h3 css={HeadingStyle}>{title}</h3>
      <ul css={galleryStyle}>
        {Items.map(({ id, text }) => (
          <li key={id} onClick={() => handleClickItem(id)}>
            <p css={textStyle}>{text}</p>
            <div css={codeBlockStyle}></div>
          </li>
        ))}
      </ul>
    </section>
  );
}
