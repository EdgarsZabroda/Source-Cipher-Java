package org.source.cipher.algorithm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GenerateKey
{
	private String sFile;
	private short sBytes;
	
	public GenerateKey(String sFile, short sBytes)
	{
		this.sFile = sFile;
		this.sBytes = sBytes;
	}
	
	public void Generate(boolean bSingle) throws IOException
	{
		short sBytes = this.sBytes;
		FileOutputStream fosKey = new FileOutputStream(this.sFile);
		byte bySingle;
		byte Key[];
		Random Picker = new Random();
		byte capitals[] = { 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
				'Y', 'Z'};
		byte smalls[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z'};
		byte numbers[] = { '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9'};
		byte symbols[] = { '~', '!', '@', '#', '$', '%',
				'^', '&', '*', '(', ')', '_', '+', '|', '{',
				'}', ':', '"', '<', '>', '?', '`', '-', '=',
				'\\', '[', ']', ';', '\'', ',', '.', '/',
				'\r', '\n', '\t'};
		
		if(bSingle == true)
		{
			bySingle = 0x01;
		}
		else
		{
			bySingle = 0x00;
		}
		
		fosKey.write(bySingle);
		
		byte bBytes[] = new byte[2];
		
		bBytes[0] = (byte)(sBytes & 0xFF);
		bBytes[1] = (byte)((sBytes >> 8) & 0xFF);
		
		fosKey.write(bBytes);
		
		Key = new byte[sBytes];
		
		for(int x=0;x<sBytes;x++)
		{
			int nRandomSymbol = Picker.nextInt(4);
			
			switch(nRandomSymbol)
			{
			case 0:
				Key[x] = capitals[Picker.nextInt(capitals.length)];
				break;
			case 1:
				Key[x] = smalls[Picker.nextInt(smalls.length)];
				break;
			case 2:
				Key[x] = numbers[Picker.nextInt(numbers.length)];
				break;
			case 3:
				Key[x] = symbols[Picker.nextInt(symbols.length)];
				break;
			}
		}
		
		fosKey.write(Key);
		
		if(bSingle == false)
		{
			short sOffset[] = new short[Key.length];
			short sTaken[] = new short[Key.length];
			short sNextOffset = 0;
			
			while(sNextOffset<Key.length)
			{
				short sGuess = (short)Picker.nextInt(Key.length);
				
				if(sTaken[sGuess] != 1)
				{
					sOffset[sNextOffset] = sGuess;
					sTaken[sGuess] = 1;
					sNextOffset++;
				}
			}
			
			for(int x=0;x<Key.length;x++)
			{
				byte bShortToByte[] = new byte[2];
				bShortToByte[0] = (byte)(sOffset[x] & 0xFF);
				bShortToByte[1] = (byte)((sOffset[x] >> 8));
				
				fosKey.write(bShortToByte);
			}
		}
		
		fosKey.close();
	}
}